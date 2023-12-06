#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/sysinfo.h> // get_nprocs()

typedef struct
{
    size_t length;
    int *numbers;
    char *letters;
} ZipValues;

typedef struct
{
    size_t size;
    ZipValues zval;
    char *buf;

} ThreadArgs;

size_t size_of_all_files(int argc, char *argv[])
{
    size_t size_all = 0;
    for (int i = 1; i < argc; ++i)
    {
        FILE *fpin = fopen(argv[i], "rb");
        fseek(fpin, 0, SEEK_END);
        size_all += ftell(fpin);
        fclose(fpin);
    }

    return size_all;
}

// TODO: refactor
void read_all_files(char *buf, int argc, char *argv[])
{
    char *p = buf;
    for (int i = 1; i < argc; ++i)
    {
        FILE *f = fopen(argv[i], "rb");

        fseek(f, 0, SEEK_END);
        long bytes = ftell(f);
        fseek(f, 0, SEEK_SET);

        size_t ret_size = fread(p, (size_t)bytes, 1, f);
        (void)ret_size;
        p += bytes;

        fclose(f);
    }
    *p = 0;
}

ZipValues zip_buffer(char *buf, size_t size)
{
    ZipValues result;
    // Allocate conservatively half the size of the input.
    result.length = size / 2;
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

void print_zip_values(ZipValues zv)
{
    for (int i = 0; i < zv.length; ++i)
    {
        fwrite(&(zv.numbers[i]), sizeof(int), 1, stdout);
        fwrite(&(zv.letters[i]), sizeof(char), 1, stdout);
    }
    fflush(stdout);
}

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

void *thread_function(void *args)
{
    ThreadArgs *thread_args = (ThreadArgs *)args;
    thread_args->zval = zip_buffer(thread_args->buf, thread_args->size);
    // print_zip_values(thread_args->zval);
    return 0;
}

int main(int argc, char *argv[])
{
    // Correct invocation should pass one or more files via the command line to the program.
    if (argc < 2)
    {
        printf("pzip: file1 [file2 ...]\n");
        exit(1);
    }

    // Get number of cores.
    int num_cores = get_nprocs();
    num_cores = 2;
    // printf("Number of threads: %d\n", num_cores);

    // Get the number of characters in all files.
    size_t total_size = size_of_all_files(argc, argv);
    // printf("Number of chars: %zu\n", total_size);

    // Allocate a buffer large enough to read all files + 1 for \0
    char *input = (char *)malloc(total_size + 1);

    // Read all files in the buffer.
    read_all_files(input, argc, argv);
    // printf("Buffer:\n%s\n", input);

    ThreadArgs thread_args[num_cores];
    pthread_t threads[num_cores];
    for (int i = 0; i < num_cores; ++i)
    {
        thread_args[i] = get_thread_args(i, num_cores, input, total_size);
        pthread_create(&threads[i], NULL, thread_function, (void *)&thread_args[i]);
    }

    // Wait for all threads to finish
    for (int i = 0; i < num_cores; ++i)
    {
        pthread_join(threads[i], NULL);
    }

    for (int i = 0; i < num_cores; ++i)
    {
        print_zip_values(thread_args[i].zval);
    }
    /*
        ZipValues zv = zip_buffer(input, total_size);
        print_zip_values(zv);

        free(zv.letters);
        free(zv.numbers);
    */
    return 0;
}
