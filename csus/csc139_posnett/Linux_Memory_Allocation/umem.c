#include <assert.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <unistd.h>

#include "umem.h"
// #include <bits/mman-linux.h>

// Magic memory value to identify our blocks
size_t const MARKER_VALUE = 0x12345678;
void *MARKER = (void *)MARKER_VALUE;
// 8-byte aligned requested in the assignment
unsigned int const ALIGNMENT = 8;
// umeminit  routine should return a failure if is called more than once
int umeminit_call_once = 0;

// Node structure for allocation strategy 1, 2, 3, 4
typedef struct node_t_str
{
  unsigned int size;
  struct node_t_str *next;
} node_t;

// Node structure for allocation strategy 5 - buddy allocator
typedef struct buddy_node
{
  unsigned int size;
  int free;
} buddy_node_t;

// List of nodes for buddy allocator
typedef struct buddy_free_list
{
  buddy_node_t *head;
  buddy_node_t *last;
} buddy_free_list_t;

// Strategy value
int g_allocationAlgo = 0;
// Head of free blocks list for strategy 1, 2, 3, 4
node_t *g_head = (node_t *)NULL;
// Pointer to next free block search start for strategy 4. NEXT_FIT
node_t *g_nextFitStart = (node_t *)NULL;
// Head of free blocks for strategy 5
buddy_free_list_t g_buddy;

// Helper allocation functions for strategies 1, 2, 3, 4
void *mallocBestFit(size_t size);
void *mallocWorstFit(size_t size);
void *mallocFirstFit(size_t size);
void *mallocNextFit(size_t size);
void coalesce();

// Helper allocation functions for strategy 5
void initBuddy(void *data, size_t size);
buddy_node_t *nextBuddyBlock(buddy_node_t *block);
void ufreeBuddy(void *data);
void *umallocBuddy(size_t size);

// Size of nodes
size_t const nodeSize = sizeof(node_t);
size_t const nodeSizeBuddy = sizeof(buddy_node_t);

/*
  Implementation of umeminit
*/
int umeminit(size_t sizeOfRegion, int allocationAlgo)
{
  umeminit_call_once += 1;
  if (allocationAlgo > 5 || allocationAlgo < 1 || sizeOfRegion == 0 || umeminit_call_once != 1)
  {
    return -1;
  }

  g_allocationAlgo = allocationAlgo;
  // TODO: revert to example call from assignment on Ubuntu 22
  // Using /dev/zero device did not work on my MacOS.
  // open the /dev/zero device
  int fd = open("/dev/zero", O_RDWR);

  // sizeOfRegion (in bytes) needs to be evenly divisible by the page size
  int myPageSize = getpagesize();
  if (allocationAlgo == 5)
  {
    int next_power_of_2 = 1;
    while (next_power_of_2 < sizeOfRegion)
    {
      next_power_of_2 *= 2;
    }
    sizeOfRegion = next_power_of_2;
  }

  size_t pageRemainder = (sizeOfRegion + nodeSize) % myPageSize;
  size_t newSizeOfRegion = ((sizeOfRegion + nodeSize) / myPageSize) * myPageSize + (pageRemainder > 0 ? myPageSize : 0);
  assert(sizeOfRegion <= newSizeOfRegion && "Not enough pages.");
  // Note: this works on MacOS and Ubuntu, I couldn't use the example call from assignment on MacOS
  // Got error: mmap: Operation not supported by device
  void *ptr = mmap(NULL, newSizeOfRegion, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANON, -1, 0);
  // void *ptr = mmap(NULL, sizeOfRegion, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);
  if (ptr == MAP_FAILED)
  {
    perror("mmap");
    // Otherwise it should return -1
    return -1;
  }
  // printf("Node Size: %zu\n", nodeSize);
  g_head = (node_t *)ptr;
  g_head->next = NULL;
  g_head->size = sizeOfRegion;
  g_nextFitStart = g_head;
  if (allocationAlgo == 5)
  {
    // printf("Buddy node Size: %zu\n", sizeof(buddy_node_t));
    initBuddy(ptr, sizeOfRegion);
  }
  // close the device (don't worry, mapping should be unaffected)
  // close(fd);
  // Return 0 on a success (when call to mmap is successful)
  return 0;
}

/*
  Implementation of umalloc
*/
void *umalloc(size_t size)
{
  if ((g_allocationAlgo != 5 && g_head == NULL) || size == 0)
  {
    return NULL;
  }
  switch (g_allocationAlgo)
  {
  case BEST_FIT:
    return mallocBestFit(size);
  case WORST_FIT:
    return mallocWorstFit(size);
  case FIRST_FIT:
    return mallocFirstFit(size);
  case NEXT_FIT:
    return mallocNextFit(size);
  case BUDDY:
    return umallocBuddy(size);
  default:
    printf("Not implemented.");
  }
  return NULL;
}

/*
  Implementation of ufree
*/
int ufree(void *ptr)
{
  if (g_allocationAlgo != 5)
  {
    node_t *nodePtr = (node_t *)((char *)ptr - nodeSize);
    // printf("ufree ptr: %p, ptr next: %p\n", nodePtr, nodePtr->next);
    if (nodePtr->next != MARKER)
    {
      return -1;
    }
    node_t *tmp = g_head;
    g_head = nodePtr;
    nodePtr->next = tmp;
    coalesce();
  }
  else
  {
    ufreeBuddy(ptr);
  }
  return 0;
}

/*
  Implementation of umemdump
  Note: printing the address of the free block that would be returned to
  the caller rather than the node address.
  I have asked through Canvas and got confirmation on this.
*/
void umemdump()
{
  if (g_allocationAlgo != 5)
  {
    node_t *node = g_head;
    while (node != NULL)
    {
      printf("%p: %u\n", (char *)node + nodeSize, node->size);
      node = node->next;
    }
  }
  else
  {
    buddy_node_t *node = g_buddy.head;
    while (node != g_buddy.last)
    {
      if (node->free)
      {
        printf("%p: %u\n", (char *)node + nodeSizeBuddy,
               node->size - (unsigned int)nodeSizeBuddy);
      }
      node = nextBuddyBlock(node);
    }
  }
}

// Round value up to next multiple of ALIGNMENT
size_t const roundup(size_t const sz)
{
  if (sz % ALIGNMENT == 0)
  {
    return sz;
  }
  return sz + (ALIGNMENT - sz % ALIGNMENT);
}

void skipNode(node_t *node, node_t *newNode)
{
  // printf("skip node: %p, to node: %p\n", node, newNode);
  for (node_t *p = g_head; p != NULL; p = p->next)
  {
    if (p->next == node)
    {
      p->next = newNode;
      break;
    }
  }
}

// Create new free nodes from leftover space of the newly allocated node
// if possible spaceLeft >= nodeSize + ALIGNMENT
void umallocSpaceSaver(node_t *allocated, size_t requestedSpace)
{
  size_t spaceLeft = allocated->size - requestedSpace;
  // printf("space saver allocated: %p, space left: %zu\n", allocated, spaceLeft);
  // printf("enter space saver g_nextFitStart: %p, g_head: %p\n", g_nextFitStart, g_head);
  if (spaceLeft >= nodeSize + ALIGNMENT)
  {
    node_t *newNode = (node_t *)((char *)allocated + requestedSpace + nodeSize);
    newNode->size = spaceLeft - nodeSize;
    newNode->next = allocated->next;
    if (allocated == g_head)
    {
      if (g_allocationAlgo == NEXT_FIT && g_nextFitStart == g_head)
      {
        g_nextFitStart = newNode;
      }
      g_head = newNode;
      // printf("1 space saver g_nextFitStart: %p, g_head: %p\n", g_nextFitStart, g_head);
    }
    else
    {
      skipNode(allocated, newNode);
      g_nextFitStart = newNode;
    }
    allocated->size = requestedSpace;
  }
  else
  {
    if (allocated == g_head)
    {
      if (g_allocationAlgo == NEXT_FIT && g_nextFitStart == g_head)
      {
        // g_nextFitStart = NULL;
        g_nextFitStart = g_head->next;
      }
      g_head = g_head->next;
      // printf("2 space saver g_nextFitStart: %p, g_head: %p\n", g_nextFitStart, g_head);
    }
    else
    {
      skipNode(allocated, allocated->next);
      g_nextFitStart = allocated->next != NULL ? allocated->next : g_head;
    }
  }
  // allocated->size = requestedSpace;
  allocated->next = MARKER;
}

void *mallocBestFit(size_t size)
{
  node_t *curBestFit = NULL;
  node_t *ptr = g_head;
  int adjustedSize = roundup(size);
  while (ptr != NULL)
  {
    if (ptr->size >= adjustedSize)
    {
      if (curBestFit == NULL || curBestFit->size > ptr->size)
      {
        curBestFit = ptr;
      }
    }
    ptr = ptr->next;
  }
  if (curBestFit == NULL)
  {
    return NULL;
  }
  umallocSpaceSaver(curBestFit, adjustedSize);

  return (void *)((char *)curBestFit + nodeSize);
}

void *mallocWorstFit(size_t size)
{
  node_t *curWorstFit = NULL;
  node_t *ptr = g_head;
  int adjustedSize = roundup(size);

  while (ptr != NULL)
  {
    if (ptr->size >= adjustedSize)
    {
      if (curWorstFit == NULL || curWorstFit->size < ptr->size)
      {
        curWorstFit = ptr;
      }
    }
    ptr = ptr->next;
  }
  if (curWorstFit == NULL)
  {
    return NULL;
  }
  umallocSpaceSaver(curWorstFit, adjustedSize);

  return (void *)((char *)curWorstFit + nodeSize);
}

void *mallocFirstFit(size_t size)
{
  node_t *ptr = g_head;
  node_t *firstFit = NULL;
  int adjustedSize = roundup(size);

  while (ptr != NULL)
  {
    if (ptr->size >= adjustedSize)
    {
      firstFit = ptr;
      break;
    }
    ptr = ptr->next;
  }
  if (firstFit == NULL)
  {
    return NULL;
  }
  umallocSpaceSaver(firstFit, adjustedSize);

  return (void *)((char *)firstFit + nodeSize);
}

void *mallocNextFit(size_t size)
{
  int adjustedSize = roundup(size);
  node_t *nextFit = NULL;
  node_t *ptr = g_nextFitStart;

  while (ptr != NULL)
  {
    if (ptr->size >= adjustedSize)
    {
      nextFit = ptr;
      break;
    }
    ptr = ptr->next;
  }
  // Since we didn't start from head, wrap around to scan the rest of the nodes
  if (ptr == NULL)
  {
    ptr = g_head;
    while (ptr != g_nextFitStart)
    {
      if (ptr->size >= adjustedSize)
      {
        nextFit = ptr;
        break;
      }
      ptr = ptr->next;
    }
  }
  if (nextFit == NULL)
  {
    return NULL;
  }

  umallocSpaceSaver(nextFit, adjustedSize);
  // printf("g_nextFitStart: %p, head: %p\n, ", g_nextFitStart, g_head);
  return (void *)((char *)nextFit + nodeSize);
}

void coalesce()
{
  int hasCoalesced = 0;
  do
  {
    // printf("coalesce g_nextFitStart: %p, head: %p\n", g_nextFitStart, g_head);
    node_t *ptr = g_head;
    char *end = NULL;
    hasCoalesced = 0;
    while (ptr != NULL)
    {
      end = (char *)ptr + nodeSize + ptr->size;
      // printf("coalesce Head: %p\n", g_head);
      node_t *ptrEnd = (node_t *)(end);
      if (g_head == ptrEnd)
      {
        ptr->size = ptr->size + ptrEnd->size + nodeSize;
        // printf("Head coalescing node: %p with node %p, new size of node: %u\n", ptr, ptrEnd, ptr->size);
        if (g_allocationAlgo == NEXT_FIT && g_nextFitStart == g_head)
        {
          g_nextFitStart = g_head->next;
        }
        g_head = g_head->next;
        hasCoalesced = 1;
      }
      node_t *cmp = g_head;
      while (cmp != NULL && cmp->next != NULL)
      {
        // printf("coalesce ptr: %p, cmp: %p, cmp next: %p, ptrEnd: %p\n", ptr, cmp, cmp->next, ptrEnd);
        if (cmp->next == ptrEnd)
        {
          ptr->size = ptr->size + ptrEnd->size + nodeSize;
          cmp->next = ptrEnd->next;
          hasCoalesced = 1;
          // printf("Coalescing node: %p with node %p, new size of node: %u\n", ptr, ptrEnd, ptr->size);
          if (g_allocationAlgo == NEXT_FIT && g_nextFitStart == ptrEnd)
          {
            g_nextFitStart = ptrEnd->next ? ptrEnd->next : g_head;
          }
          break;
        }
        cmp = cmp->next;
      }
      ptr = ptr->next;
    }
  } while (hasCoalesced);
}

buddy_node_t *nextBuddyBlock(buddy_node_t *block)
{
  return (buddy_node_t *)((char *)block + block->size);
}

buddy_node_t *splitBlockBuddy(buddy_node_t *block, size_t size)
{
  if (block != NULL && size != 0)
  {
    // Split into smaller blocks until size <= block->size
    while (size < block->size)
    {
      size_t sz = block->size >> 1;
      block->size = sz;
      block = nextBuddyBlock(block);
      block->size = sz;
      block->free = 1;
    }

    if (size <= block->size)
    {
      return block;
    }
  }

  // No block can fit
  return NULL;
}

int is_power_of_two(unsigned int x)
{
  return (x != 0) && ((x & (x - 1)) == 0);
}

void initBuddy(void *data, size_t size)
{
  buddy_free_list_t *b = &g_buddy;
  assert(data != NULL);
  assert(is_power_of_two(size) && "size should be a power of 2");

  b->head = (buddy_node_t *)data;
  b->head->size = size;
  b->head->free = 1;
  b->last = nextBuddyBlock(b->head);
}

buddy_node_t *findBlockBuddy(buddy_node_t *head, buddy_node_t *tail, size_t size)
{
  // Assumes size != 0

  buddy_node_t *best_block = NULL;
  buddy_node_t *block = head;                   // Left Buddy
  buddy_node_t *buddy = nextBuddyBlock(block); // Right Buddy

  // The entire memory section between head and tail is free,
  // just call 'buddy_block_split' to get the allocation
  if (buddy == tail && block->free)
  {
    return splitBlockBuddy(block, size);
  }

  // Find the block which is the 'best_block' to requested allocation sized
  while (block < tail && buddy < tail)
  {
    if (block->free && size <= block->size &&
        (best_block == NULL || block->size <= best_block->size))
    {
      best_block = block;
    }
    block = buddy;
    buddy = nextBuddyBlock(buddy);
  }

  if (best_block != NULL)
  {
    return splitBlockBuddy(best_block, size);
  }

  return NULL;
}

size_t computeBlockSize(buddy_free_list_t *b, size_t size)
{
  size_t actual_size = roundup(nodeSizeBuddy);

  size += nodeSizeBuddy;
  size = roundup(size);

  while (size > actual_size)
  {
    actual_size <<= 1;
  }

  return actual_size;
}

void coalesceBuddy(buddy_node_t *head, buddy_node_t *tail)
{
  int has_coalesced = 0;
  do
  {
    buddy_node_t *ptr = head;
    buddy_node_t *buddy = nextBuddyBlock(ptr);
    int has_coalesced = 0;

    while (ptr < tail && buddy < tail)
    {
      if (ptr->free && buddy->free && ptr->size == buddy->size)
      {
        // Found two buddies, coalesce them.
        ptr->size <<= 1;
        has_coalesced = 1;
      }
      ptr = nextBuddyBlock(ptr);
      buddy = nextBuddyBlock(ptr);
    }
  } while (has_coalesced);
}

void *umallocBuddy(size_t size)
{
  buddy_free_list_t *b = &g_buddy;
  if (size != 0)
  {
    size_t actual_size = computeBlockSize(b, size);

    buddy_node_t *found = findBlockBuddy(b->head, b->last, actual_size);
    if (found == NULL)
    {
      coalesceBuddy(b->head, b->last);
      found = findBlockBuddy(b->head, b->last, actual_size);
    }

    if (found != NULL)
    {
      found->free = 0;
      return (void *)((char *)found + nodeSizeBuddy);
    }
  }

  return NULL;
}

void ufreeBuddy(void *data)
{
  buddy_free_list_t *b = &g_buddy;
  if (data != NULL)
  {
    buddy_node_t *block;
    block = (buddy_node_t *)((char *)data - nodeSizeBuddy);
    block->free = 1;
    coalesceBuddy(b->head, b->last);
  }
}
