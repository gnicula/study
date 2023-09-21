#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
	if(argc != 2) {
		printf("Syntax should be: CommandName FiletoRead\n");
		exit(1);
	}

	FILE *fp;
	fp = fopen(argv[1], "r");
	
	if(fp == NULL) {
		perror("Error opening file");
		return(-1);
	}
	
	char str[1024];

	if(fgets(str, 60, fp) != NULL) {
		while(fgets(str, 60, fp) != NULL) {
			printf("%s", str);
		}
	} else {
		printf("Cannot read file");
	}

	fclose(fp);
	return(0);
}
