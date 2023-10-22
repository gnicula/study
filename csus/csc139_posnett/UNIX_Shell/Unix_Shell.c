#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>

#define MAX_INPUT_SIZE 1024
#define MAX_ARGS 64
#define MAX_PATHS 64
#define MAX_PATH_LENGTH 256

unsigned int num_paths = 0;
char wish_paths[MAX_PATHS][MAX_PATH_LENGTH];

void error() {
    char error_message[30] = "An error has occurred\n";
    write(STDERR_FILENO, error_message, strlen(error_message));
}

int add_path(char* str) {
    char* token;
    int i = 0;

    while ((token = strsep(&str, " "))) {
        strcpy(wish_paths[i], token);
        ++i;
    }
    return i;
}

int num_arguments(char* str) {
    int nargs = 0;
    int inword = 0;
    // printf("str: %s", str);
    int length = 0;
    if (str != NULL) {
        length = strlen(str);
    }

    // printf("numargs: %d", length);
    
    for (int i = 0; i < length; ++i) {
        if (str[i] == ' ' && inword == 1) {
            ++nargs;
            inword = 0;
        } else if (str[i] != ' ' && inword == 0) {
            inword = 1;
        } 
    }
    if (inword == 1) {
        ++nargs;
    }
    return nargs;
}

void execute_non_built_in_command(char* name, char* cmd, char* pipe) {
    // printf("name: %s, cmd: %s", name, cmd);
    fflush(stdout);
    int pid = fork();
    if (pid == 0) {
        // child process
        int nargs = num_arguments(cmd);
        // printf("in child, n_args: %d\n", nargs);

        char* argv[nargs + 2];
        argv[0] = name;
        for (int i = 1; i < nargs + 1; ++i) {
            argv[i] = strsep(&cmd, " ");
        }
        argv[nargs + 1] = NULL;
        fflush(stdout);
        // printf("execv name: %s, argv: %s", name, argv[0]);
        if (execv(name, argv) == -1) {
            error();
        }
        exit(0);
    } else if (pid > 0) {
        // parent process
        wait(NULL);
    } else {
        // could not create process
        // printf("Could not create process");
        error();
    }
}

void resolve_non_built_in_command(char* name, char* cmd, char* pipe) {
    if ((access(name, X_OK) != 0)) {
        int found_in_path = 0;
        for (int i = 0; i < num_paths; ++i) {
            char* full_name = strdup(wish_paths[i]);
            strcat(full_name, "/");
            strcat(full_name, name);

            if (access(full_name, X_OK) == 0) {
                found_in_path = 1;
                execute_non_built_in_command(full_name, cmd, pipe);
                break;
            }
        }
        if (found_in_path == 0) {
            error();
        }
    } else {
        execute_non_built_in_command(name, cmd, pipe);
    }
}

void execute_single_command(char* str) {
    char* token = strsep(&str, " ");
    // printf("sc: %s\n", token);
    int result = 0;
    if (strcmp(token, "cd") == 0) {
        // printf("str: %s|\n", str);
        result = chdir(str);
        // printf("result: %d\n", result);
        if (result != 0) {
            error();
        }
    } else if(strcmp(token, "path") == 0) {
        num_paths = add_path(str);
        // printf("%s", wish_paths[0]);
    } else if(strcmp(token, "exit") == 0) {
        if (str == NULL) {
            exit(0);
        } else {
            error();
        }
    } else {
        // try search in path and execute with fork
        resolve_non_built_in_command(token, str, NULL);
    }
}

void execute_line_commands(char* str) {
    char* token = NULL;
    char* copy_line = strdup(str);
    while ((token = strsep(&copy_line, "&"))) {
        // printf("lc: %s\n", token);
        execute_single_command(token);
    }
}

int main(int argc, char *argv[]) {
    
    char *input = NULL;
    size_t input_size = 0;

    if (argc > 2) {
        error();
        exit(1);
    }

    num_paths = add_path("/bin");

    if (argc == 2) {
        // Batch mode: Read commands from a file
        FILE *batch_file = fopen(argv[1], "r");
        if (batch_file == NULL) {
            error();
            exit(1);
        }
        while (getline(&input, &input_size, batch_file) != -1) {
            // Remove \r\n from end of line
            input[strcspn(input, "\r\n")] = 0;
            execute_line_commands(input);
        }
        free(input); // Free the dynamically allocated input buffer
        fclose(batch_file);
    } else {
        // Interactive mode
        int should_exit = 0; // Indicates if the shell should exit

        while (!should_exit) {
            printf("wish> ");
            if (getline(&input, &input_size, stdin) == -1) {
                break; // Exit on EOF or error
            } else {
                // Remove \r\n from end of line
                input[strcspn(input, "\r\n")] = 0;
            }

            // Check if the user entered "exit"
            if (strcmp(input, "exit") == 0) {
                should_exit = 1;
            } else {
                execute_line_commands(input);
            }

            // Reset the input buffer for the next iteration
            free(input);
            input = NULL;
            input_size = 0;
        }
    }

    // free(input);
    return 0;
}

