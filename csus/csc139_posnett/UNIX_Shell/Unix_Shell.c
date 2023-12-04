#include <ctype.h>
#include <fcntl.h>
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
    for (int i = 0; i < num_paths; ++i) {
        for (int j = 0; j < MAX_PATH_LENGTH; ++j) {
            wish_paths[i][j] = 0;
        }
    }
    int i = 0;
    while ((token = strsep(&str, " "))) {
        strcpy(wish_paths[i], token);
        ++i;
    }
    return i;
}

int character_search(char* str, char c) {
    while (*str != 0) {
        // printf("str: %s\n", str);
        if (*str == c) {
            return 1;
        }
        ++str;
    }
    return 0;
}

char* trim_white_space(char *str)
{
    if (str == NULL) {
        return str;
    }
    char* end;

    // Trim leading space
    while(isspace((unsigned char)*str)) {
        str++;
    }

    if(*str == 0) {
        return str;
    }

    // Trim trailing space
    end = str + strlen(str) - 1;
    while(end > str && isspace((unsigned char)*end)) {
        end--;
    }

    // Write new null terminator character
    end[1] = '\0';

    return str;
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

void execute_non_built_in_command(char* name, char* cmd, char* filename) {
    int fout = -1;
    if (filename != NULL) {
        // command output is redirected to a file, create it
        fout = open(filename, O_WRONLY | O_TRUNC | O_CREAT);
        if (fout == -1) {
            // Test 22 fails on Ubuntu 20.04 with 'Permission denied'
            // Test 22 tries to redirect to same file concurrently.
            // perror("Cannot open output file\n");
            error();
            // invalid redirection
            return;
        } 
    }
    // printf("name: %s, cmd: %s, filename: %s\n", name, cmd, filename);
    fflush(stdout);
    int pid = fork();
    if (pid == 0) {
        // child process
        int nargs = num_arguments(cmd);
        // printf("in child, n_args: %d\n", nargs);

        // define the argument table, execv expects
        // a table of pointers to strings
        // must be null-terminated (\0)
        char* argv[nargs + 2];
        argv[0] = name;
        for (int i = 1; i < nargs + 1; ++i) {
            argv[i] = strsep(&cmd, " ");
        }
        argv[nargs + 1] = NULL;
        if (fout > 0) {
            dup2(fout, STDOUT_FILENO);
            dup2(fout, STDERR_FILENO);
        }
        // printf("execv name: %s, argv: %s", name, argv[0]);
        if (execv(name, argv) == -1) {
            error();
        }
        exit(0);
    } else if (pid > 0) {
        // parent process
        // wait(NULL);
    } else {
        // could not create process
        // printf("Could not create process");
        error();
    }
}

void resolve_non_built_in_command(char* name, char* cmd, char* filename) {
    // printf("Name: |%s|, cmd: %s\n", name, cmd);
    if (name == NULL || name[0] == '\0') {
        error();
        return;
    }
    if ((access(name, X_OK) != 0)) {
        // command was not found in current directory
        // search for it in the registered paths
        int found_in_path = 0;
        for (int i = 0; i < num_paths; ++i) {
            char* full_name = strdup(wish_paths[i]);
            strcat(full_name, "/");
            strcat(full_name, name);

            if (access(full_name, X_OK) == 0) {
                found_in_path = 1;
                execute_non_built_in_command(full_name, cmd, filename);
                break;
            }
        }
        if (found_in_path == 0) {
            error();
        }
    } else {
        execute_non_built_in_command(name, cmd, filename);
    }
}

void execute_single_command(char* str) {
    int has_indirection = character_search(str, '>');
    char* cmd = strsep(&str, ">");
    char* cmd_name = strsep(&cmd, " ");
    // printf("single command name: %s\n", cmd_name);
    int result = 0;
    if (strcmp(cmd_name, "cd") == 0) {
        // printf("command args: %s|\n", cmd);
        result = chdir(cmd);
        // printf("result: %d\n", result);
        if (result != 0) {
            error();
        }
    } else if(strcmp(cmd_name, "path") == 0) {
        num_paths = add_path(cmd);
        // printf("%s", wish_paths[0]);
    } else if(strcmp(cmd_name, "exit") == 0) {
        if (cmd == NULL) {
            exit(0);
        } else {
            error();
        }
    } else {
        char* filename = trim_white_space(str);
        cmd_name = trim_white_space(cmd_name);
        if (has_indirection && (filename == NULL || character_search(filename, ' '))) {
            error();
        } else {
            // try search in path and execute with fork
            resolve_non_built_in_command(cmd_name, cmd, filename);
        }
    }
}

void execute_line_commands(char* str) {
    char* token = NULL;
    char* copy_line = strdup(str);
    while ((token = strsep(&copy_line, "&"))) {
        token = trim_white_space(token);
        // printf("lc: %s\n", token);
        if (strlen(token) > 0) {
            execute_single_command(token);
        }
    }
    while (wait(NULL) > 0) {
        // wait for all children to complete
    }
}

void process_line(char* input) {
    // printf("process line: [%s]\n", input);
    input[strcspn(input, "\r\n")] = 0;
    char* trimmed_input = trim_white_space(input);
    if (trimmed_input != NULL && strlen(trimmed_input) > 0) {
        execute_line_commands(trimmed_input);
    }
}

void run_interactive_mode() {
    // Interactive mode
    char* input = NULL;
    size_t input_size = 0;
    printf("wish> ");
    while (getline(&input, &input_size, stdin) != -1) {
        process_line(input);
        free(input);
        input = NULL;
        input_size = 0;
        printf("wish> ");
    }
    free(input); // Free the dynamically allocated input buffer
    input = NULL;
    input_size = 0;

}

void run_batch_mode(char* filename) {
    char *input = NULL;
    size_t input_size = 0;

    // Batch mode: Read commands from a file
    FILE *batch_file = fopen(filename, "r"); 
    if (batch_file == NULL) {
        error();
        exit(1);
    }

    while (getline(&input, &input_size, batch_file) != -1) {
        process_line(input);
        free(input);
        input = NULL;
        input_size = 0;
    }
    free(input); // Free the dynamically allocated input buffer
    input = NULL;
    input_size = 0;
    fclose(batch_file);

}

int main(int argc, char *argv[]) {

    if (argc > 2) {
        error();
        exit(1);
    }
    num_paths = add_path("/bin");

    if (argc == 2) {
        run_batch_mode(argv[1]);
    } else {
        run_interactive_mode();
    }
    return 0;
}
