#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/sysinfo.h> // get_nprocs()

// Holds the result of a buffer compression
typedef struct
{
    // Number of characters
    size_t length;
    // The characters counts
    int *numbers;
    // The characters.
    char *letters;
} ZipValues;

// Contains everything needed for a thread to start working
typedef struct
{
    // The size of the buffer to be compressed
    size_t size;
    // Where to put the result of compression
    ZipValues zval;
    // The start address of the characted buffer
    char *buf;
} ThreadArgs;

// Returns the number of characters in all input files
size_t size_of_all_files(int argc, char *argv[])
{
    size_t size_all = 0;
    for (int i = 1; i < argc; ++i)
    {
        FILE *fpin = fopen(argv[i], "rb");
        if (fpin == NULL) {
            printf("Cannot open file %s\n", argv[i]);
            exit(1);
        }
        fseek(fpin, 0, SEEK_END);
        size_all += ftell(fpin);
        fclose(fpin);
    }

    return size_all;
}

// Reads all input files and groups (appends) them in the given pre-allocated buffer
void read_all_files(char *buf, int argc, char *argv[])
{
    for (int i = 1; i < argc; ++i)
    {
        FILE *fpin = fopen(argv[i], "rb");
        if (fpin == NULL) {
            printf("Cannot open file %s\n", argv[i]);
            exit(1);
        }
        fseek(fpin, 0, SEEK_END);
        long bytes = ftell(fpin);
        fseek(fpin, 0, SEEK_SET);

        size_t ret_size = fread(buf, (size_t)bytes, 1, fpin);
        (void)ret_size;
        buf += bytes;

        fclose(fpin);
    }
    *buf = 0;
}

ZipValues zip_buffer(char *buf, size_t size)
{
    ZipValues result;
    // Allocate conservatively half the size of the input.
    result.length = (size == 1) ? size : size / 2;
    result.letters = (char *)malloc(result.length * sizeof(char));
    result.numbers = (int *)malloc(result.length * sizeof(int));
    size_t r = 0;
    for (size_t i = 0; i < size;)
    {
        int count = 0;
        char start_c = buf[i];
        for (char c = buf[i]; c == start_c && i < size; ++count)
        {
            c = buf[++i];
        }
        if (r >= result.length)
        {
            // Allocate more space for the result.
            result.length *= 2;
            result.letters = (char *)realloc(result.letters, result.length * sizeof(char));
            result.numbers = (int *)realloc(result.numbers, result.length * sizeof(int));
        }
        result.letters[r] = start_c;
        result.numbers[r] = count;
        ++r;
        // printf("start_c: %c, count: %d\n", start_c, count);
    }
    result.length = r;
    return result;
}

// Check if two consecutive compression results can be 'stitched'
void check_for_stitching(ZipValues *left, ZipValues *right)
{
    if (left->length == 0 || right->length == 0)
    {
        // Nothing to stitch.
        return;
    }
    char left_last = left->letters[left->length - 1];
    char right_first = right->letters[0];
    if (left_last == right_first)
    {
        // Add the count from left last char to right's first char
        right->numbers[0] += left->numbers[left->length - 1];
        // Remove the left last char
        --left->length;
    }
}

// Writes to stdout binary values of the compressed result
void print_zip_values(ZipValues zv)
{
    for (int i = 0; i < zv.length; ++i)
    {
        fwrite(&(zv.numbers[i]), sizeof(int), 1, stdout);
        fwrite(&(zv.letters[i]), sizeof(char), 1, stdout);
    }
    fflush(stdout);
}

// Prepare each thread argument structure given its thread index
ThreadArgs get_thread_args(int thread_index, int num_threads, char *buf, size_t buf_size)
{
    ThreadArgs result;
    size_t slice_size = buf_size / num_threads;
    size_t last_slice = buf_size + slice_size * (1 - num_threads);

    result.buf = buf + thread_index * slice_size;

    if (thread_index < num_threads - 1)
    {
        result.size = slice_size;
    }
    else
    {
        // Thread gets last slice
        result.size = last_slice;
    }

    return result;
}

// This is the function executed by each thread
// Each thread receives a different starting address in thread_args->buf
void *thread_function(void *args)
{
    ThreadArgs *thread_args = (ThreadArgs *)args;
    // Compress and store result in the given arg->ZipValue
    thread_args->zval = zip_buffer(thread_args->buf, thread_args->size);
    // print_zip_values(thread_args->zval);
    return 0;
}

int main(int argc, char *argv[])
{
    // Correct invocation should pass one or more files via the command line to the program.
    if (argc < 2)
    {
        // NOTE: test number 3 expects "wzip: file1 [file2 ...]"
        // If "pzip: file1 [file2 ...]" is desired, uncomment/comment the following
        // printf("pzip: file1 [file2 ...]\n");
        printf("wzip: file1 [file2 ...]\n");
        exit(1);
    }

    // Get number of cores.
    int num_cores = get_nprocs();
    // printf("Number of threads to create: %d\n", num_cores);

    // Get the number of characters in all files.
    size_t total_size = size_of_all_files(argc, argv);
    // printf("Number of chars: %zu\n", total_size);
    
    // Check if the input size is smaller than number of threads and
    // some thread(s) would get nothing; in this case I use just one working thread.
    if (num_cores >= total_size)
    {
        num_cores = 1;
    }

    // Allocate a buffer large enough to read all files + 1 for \0
    char *input = (char *)malloc(total_size + 1);
    if (input == NULL)
    {
        // The program expects that all file(s) content can be loaded in allocated memory.
        fprintf(stderr, "Cannot allocate buffer of size: %zu\n", total_size + 1);
        exit(1);
    }

    // Read all files in the buffer.
    read_all_files(input, argc, argv);
    // printf("Buffer:\n%s\n", input);

    // Initialize thread arguments and create all running threads
    ThreadArgs thread_args[num_cores];
    pthread_t threads[num_cores];
    for (int i = 0; i < num_cores; ++i)
    {
        // Each thread receives a different block from the input buffer that holds content from all files
        thread_args[i] = get_thread_args(i, num_cores, input, total_size);
        pthread_create(&threads[i], NULL, thread_function, (void *)&thread_args[i]);
    }

    // Wait for all threads to finish
    for (int i = 0; i < num_cores; ++i)
    {
        pthread_join(threads[i], NULL);
    }

    // Stitch thread results if needed
    for (int i = 0; i < num_cores - 1; ++i)
    {
        check_for_stitching(&thread_args[i].zval, &thread_args[i + 1].zval);
    }

    // Finally, write the results to stdout and free thread allocated memory
    for (int i = 0; i < num_cores; ++i)
    {
        print_zip_values(thread_args[i].zval);
        free(thread_args[i].zval.numbers);
        free(thread_args[i].zval.letters);
    }
    // Free the character buffer holding the content of all files
    free(input);

    return 0;
}
