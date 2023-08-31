#include <stdio.h>
2 #include <stdlib.h>
3 #include <sys/time.h>
4 #include <assert.h>
5 #include "common.h"
6
7 int
8 main(int argc, char *argv[])
9 {
10 if (argc != 2) {
11 fprintf(stderr, "usage: cpu <string>\n");
12 exit(1);
13 }
14 char *str = argv[1];
15 while (1) {
16 Spin(1);
17 printf("%s\n", str);
18 }
19 return 0;
20 }
