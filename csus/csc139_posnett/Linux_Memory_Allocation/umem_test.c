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

int main(int argc, char* argv[])
{
  char const *strategy_names[] = {"BEST_FIT", "WORST_FIT", "FIRST_FIT", "NEXT_FIT", "BUDDY"};
  if (argc < 2) {
    printf("Specify strategy as an integer.\n");
    return 1;
  }
  int strategy = atoi(argv[1]);
  if (strategy > 5 || strategy < 1) {
    printf("Specify strategy as an integer between 1 and 5.\n");
    return 1;
  }
  printf("Testing strategy %s.\n", strategy_names[strategy-1]);
  unsigned int region_size = 12800000;
  test_umeminit(region_size, strategy);
  test_umemdump();
  // Scenario 1: Allocate block and immediately deallocate it.
  // Look for coalesce to return the initial memory.

  printf("Scenario 1: \n\n");
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
  printf("Scenario 2: \n\n");
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
  printf("Scenario 3: \n\n");
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

  // Scenario 4: Create 3 blocks of free memory.
  // Test NEXT_FIT strategy.
  printf("Scenario 4: \n\n");
  void *result8 = test_umalloc(8);
  // printf("result8: %p\n", result8);
  test_umemdump();
  void *result9 = test_umalloc(8);
  // printf("result9: %p\n", result9);
  test_umemdump();
  void *result10 = test_umalloc(16);
  // printf("result10: %p\n", result10);
  test_umemdump();
  void *result11 = test_umalloc(8);
  // printf("result11: %p\n", result11);
  test_umemdump();
  void *result12 = test_umalloc(16);
  // printf("result12: %p\n", result12);
  test_umemdump();
  test_ufree(result12);
  test_ufree(result10);
  test_ufree(result8);
  test_umemdump();
  void *result13 = test_umalloc(16);
  // printf("result13: %p\n", result13);
  test_umemdump();
  void *result14 = test_umalloc(7);
  // printf("result14: %p\n", result14);
  test_umemdump();
  test_ufree(result9);
  test_umemdump();
  test_ufree(result11);
  test_umemdump();
  test_ufree(result13);
  test_umemdump();
  test_ufree(result14);
  test_umemdump();

  // Scenario 5, corner cases
  printf("Scenario 5: \n\n");
  void *result15 = test_umalloc(2 * region_size);
  printf("result15: %p\n", result15);
  void *result16 = test_umalloc(0);
  printf("result16: %p\n", result16);
  return 0;
}
