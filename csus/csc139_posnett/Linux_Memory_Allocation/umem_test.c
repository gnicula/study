#include <stdio.h>
#include <stdlib.h>

#include "umem.h"

int test_umeminit(size_t size, int allocationAlgo)
{
  int result = umeminit(size, allocationAlgo);
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
  return result;
}

int main()
{
  test_umeminit(128, NEXT_FIT);
  test_umemdump();
  // Scenario 1: Allocate block and immediately deallocate it.
  // Look for coalesce to return the initial memory.
  printf("Scenario 1: \n");
  void *result1 = test_umalloc(75);
  test_umemdump();
  int freeTest = test_ufree(result1);
  if (freeTest == -1)
  {
    printf("Failed\n");
  }
  test_umemdump();
  
  // Scenario 2: Allocate 3 blocks and deallocate 2 of them.
  // Observe memory fragmentation with no coalescing.
  printf("Scenario 2: \n");
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

  // Scenario 3: Create 1 small block and 1 large block of free memory,
  // And then test policies when allocating.
  printf("Scenario 3: \n");
  void *result5 = test_umalloc(31);
  void *result6 = test_umalloc(20);
  test_umemdump();
  test_ufree(result5);
  test_umemdump();
  void *result7 = test_umalloc(8);
  test_umemdump();
  test_ufree(result6);
  test_umemdump();
  test_ufree(result7);
  test_umemdump();

  return 0;
}