#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char *argv[]) {
	// Check if there is a search term specified on the command line
	if (argc < 2) {
		printf("wgrep: searchterm [file ...]\n");
		exit(1);
	}
	
	char *line = NULL;
        size_t len = 0;
        ssize_t nread = -1;
	char const *pattern = argv[1];
	
	if (argc == 2) {
		nread = getline(&line, &len, stdin);
		while (nread != -1) {
			char *p = strstr(line, pattern);
			if (p != NULL) {
				printf("%s", line);
			}
			free(line);
			line = NULL;
			nread = getline(&line, &len, stdin);
		}
	} else {
		for (int i = 2; i < argc; ++i) {
			FILE *fpin = fopen(argv[i], "r");
			
			if (fpin == NULL) {
				printf("wgrep: cannot open file\n");
				exit(1);
			}
			
			// printf("opened file: %s", argv[i]);

			nread = getline(&line, &len, fpin);
			while (nread != -1) {
				
				// printf("searching line: %s", line);
				
				char *p = strstr(line, pattern);
				if (p != NULL) {
					printf("%s", line);
				}
				free(line);
				line = NULL;
				nread = getline(&line, &len, fpin);
			}
		}
	}
	return 0;
}

