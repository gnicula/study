#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
	// Check that there are two files passed as arguments.
	if (argc < 2) {
		printf("wzip: file1 [file2 ...]\n");
		exit(1);
	}
	
	// Initialize necessary vars.	
	int filearg = 1;
	char ch;
	char next;
	int count = 0;	
	// Open the file
	FILE *fpin = fopen(argv[filearg], "r");
		
	// Check that the file can be opened.
	if (fpin == NULL) {
		printf("wzip cannot open file\n");
		exit(1);
	}
	// Loop through sequences in File.
        while ((ch = getc(fpin)) != EOF) {
                ++count;
		// Loop through characters in a sequence.
                while ((next = getc(fpin)) == ch || next == EOF) {
                        if (next == EOF) {
                                ++filearg;   
                                fclose(fpin);
                                if (filearg < argc) {
                                        fpin = fopen(argv[filearg], "r");
                                } else {
                                        fwrite(&count, sizeof(int), 1, stdout);
                                        fwrite(&ch, sizeof(char), 1, stdout);
                                        fflush(stdout);
                                        exit(0);
                                }
                        } else {
                                ++count;
                        }
                }
                fwrite(&count, sizeof(int), 1, stdout);
                fwrite(&ch, sizeof(char), 1, stdout);
                fflush(stdout);
                ungetc(next, fpin);
                count = 0;
        }

	return 0;
}
