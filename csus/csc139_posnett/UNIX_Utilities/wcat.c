#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
	// Check if there are no files specified on the command line
	if (argc < 2) {
		return 0;
	}

	// Loop through each file specified on the command line
	for (int i = 1; i < argc; i++) {
		FILE *file = fopen(argv[i], "r");

		// Check if the file couldn't be opened
		if (file == NULL) {
			printf("wcat: cannot open file\n");
			exit(1);
		}

		// Read and print the file contents
		char ch;
		while ((ch = fgetc(file)) != EOF) {
			putchar(ch);
		}

		// Close the file
		fclose(file);
	}

	return 0;
}

