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

void* test_umalloc(size_t size)
{
  void* ptr = umalloc(size);
  if (ptr != NULL) 
  {
    printf("Passed\n");
  }
  return ptr;
}

int test_ufree(void *ptr)
{
  int result = ufree(ptr);
  if (result == 0)
  {
    printf("Passed\n");
  }
  return 0;
}

int main()
{
  test_umeminit(128);
  test_umemdump();
  // Scenario 1: Allocate block and immediately deallocate it.
  // Look for coalesce to return the initial memory.
  void *result1 = test_umalloc(75);
  test_umemdump();
  test_ufree(result1);
  test_umemdump();
  
  // Scenario 2: Allocate 3 blocks and deallocate 2 of them.
  // Observe memory fragmentation with no coalescing.
  void *result2 = test_umalloc(15);
  test_umemdump();
  void *result3 = test_umalloc(16);
  test_umemdump();
  void *result4 = test_umalloc(17);
  test_umemdump();
  test_ufree(result3);
  test_umemdump();
  // BUG: Does not coalesce correctly.
  test_ufree(result4);
  test_umemdump();
  test_ufree(result2);
  test_umemdump();

  return 0;
}