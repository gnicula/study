#include <stdio.h>
#include <stdlib.h>

#include "umem.h"

int test_umeminit() {
  int result = umeminit(100, BEST_FIT);
  if (result == 0) {
    printf("Passed\n");    
  }
  return 0;
}

int main() {
  test_umeminit();
  
  return 0;
}