#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
	// Check that there are two files passed as arguments
	if (argc < 2) {
		printf("wunzip: file1 [file2 ...]\n");
		exit(1);
	}
	
	// Initialize necessary vars.	
	char ch;
	int count = 0;	
	int filearg = 1;
	// Open the file(s).
	FILE *fpin = fopen(argv[filearg], "rb");
	// Check that the file can be opened
	if (fpin == NULL) {
		printf("wunzip cannot open file\n");
		exit(1);
	}
	// Loop through the file to increment count for
	// each occurance of a specific character.
	while(1) {
		size_t fret = fread(&count, sizeof(int), 1, fpin);
		// printf("%d %zu\n", count, fret);
		if (fret == 1) {
			fret = fread(&ch, sizeof(char), 1, fpin);
			if (fret == 1) {
				for (int i = 0; i < count; ++i) {
					// Write to stdout
					putchar(ch);
				}
			}
		} else {
			if (filearg < argc - 1) {
				fclose(fpin);
				fpin = fopen(argv[++filearg], "rb");
			} else {
				exit(0);
			}
		}
	}
	return 0;
}
