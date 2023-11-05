#include <stdio.h>
#include <stdlib.h>

#include "umem.h"

int test_umeminit(size_t size)
{
  int result = umeminit(size, BEST_FIT);
  if (result == 0)
  {
    printf("Passed\n");
  }
  return 0;
}

int test_umemdump()
{
  umemdump();

  return 0;
}

int test_umalloc(size_t size)
{
  void* ptr = umalloc(size);
  if (ptr != NULL) 
  {
    printf("Passed\n");
  }
  return 0;
}

int main()
{
  test_umeminit(128);
  test_umemdump();
  test_umalloc(75);
  test_umemdump();

  return 0;
}