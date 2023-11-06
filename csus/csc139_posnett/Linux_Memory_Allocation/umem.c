#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <unistd.h>

#include "umem.h"

size_t const MARKER_VALUE = 0x12345678;
void *MARKER = (void *)MARKER_VALUE;

typedef struct node_t_str
{
  unsigned int size;
  struct node_t_str *next;
} node_t;

int g_allocationAlgo = 0;

node_t *g_head = (node_t *)NULL;
node_t *g_nextFitStart = (node_t *)NULL;

size_t roundup(size_t const sz)
{
  if (sz % 8 == 0)
  {
    return sz;
  }
  return sz + (8 - sz % 8);
}

size_t const nodeSize = sizeof(node_t);

int umeminit(size_t sizeOfRegion, int allocationAlgo)
{
  g_allocationAlgo = allocationAlgo;
  // FIX FOR UBUNTU 22
  // open the /dev/zero device
  // int fd = open("/dev/zero", O_RDWR);

  // sizeOfRegion (in bytes) needs to be evenly divisible by the page size
  int myPageSize = getpagesize();
  size_t pageRemainder = (sizeOfRegion + nodeSize) % myPageSize;
  size_t newSizeOfRegion = ((sizeOfRegion + nodeSize) / myPageSize) * myPageSize + (pageRemainder > 0 ? myPageSize : 0);
  void *ptr = mmap(NULL, newSizeOfRegion, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANON, -1, 0);
  if (ptr == MAP_FAILED)
  {
    perror("mmap");
    exit(1);
  }
  // printf("Node Size: %zu\n", nodeSize);
  g_head = (node_t *)ptr;
  g_head->next = NULL;
  g_head->size = sizeOfRegion;
  g_nextFitStart = g_head;
  // close the device (don't worry, mapping should be unaffected)
  // close(fd);
  return 0;
}

void umemdump()
{
  node_t *node = g_head;
  while (node != NULL)
  {
    printf("%p: %u\n", node, node->size);
    node = node->next;
  }
}

void skipNode(node_t *node, node_t *newNode)
{
  for (node_t *p = g_head; p != NULL; p = p->next)
  {
    if (p->next == node)
    {
      p->next = newNode;
      break;
    }
  }
}

void umallocSpaceSaver(node_t *allocated, size_t requestedSpace)
{
  size_t spaceLeft = allocated->size - requestedSpace;
  if (spaceLeft >= nodeSize + 8)
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
    }
    else
    {
      skipNode(allocated, newNode);
    }
  }
  else
  {
    if (allocated == g_head)
    {
      if (g_allocationAlgo == NEXT_FIT && g_nextFitStart == g_head)
      {
        g_nextFitStart = NULL;
      }
      g_head = NULL;
    }
    else
    {
      skipNode(allocated, allocated->next);
    }
  }
  allocated->size = requestedSpace;
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
      g_nextFitStart = nextFit->next;
      if (g_nextFitStart == NULL)
      {
        g_nextFitStart = g_head;
      }
      break;
    }
    ptr = ptr->next;
  }
  if (ptr == NULL)
  {
    ptr = g_head;
    while (ptr != g_nextFitStart)
    {
      if (ptr->size >= adjustedSize)
      {
        nextFit = ptr;
        g_nextFitStart = nextFit->next;
        if (g_nextFitStart == NULL)
        {
          g_nextFitStart = g_head;
        }
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
  return (void*)((char*)nextFit + nodeSize);
}

void *umalloc(size_t size)
{
  if (g_head == NULL)
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

  default:
    printf("Not implemented.");
  }
  return NULL;
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
      // printf("Head: %p\n", g_head);
      node_t *ptrEnd = (node_t *)(end);
      node_t *cmp = g_head;
      if (g_head == ptrEnd)
      {
        ptr->size = ptr->size + ptrEnd->size + nodeSize;
        g_head = g_head->next;
        if (g_allocationAlgo == NEXT_FIT && g_nextFitStart == g_head)
        {
          g_nextFitStart = g_head;
        }
        hasCoalesced = 1;
      }
      while (cmp != NULL && cmp->next != NULL)
      {
        // printf("ptr: %p, cmp: %p, cmp next: %p, ptrEnd: %p\n", ptr, cmp, cmp->next, ptrEnd);
        if (cmp->next == ptrEnd)
        {
          ptr->size = ptr->size + ptrEnd->size + nodeSize;
          cmp->next = ptrEnd->next;
          hasCoalesced = 1;
          // printf("Coalescing node: %p with node %p, new size of node: %u\n", ptr, ptrEnd, ptr->size);
        }
        cmp = cmp->next;
      }
      ptr = ptr->next;
    }
  } while (hasCoalesced);
}

int ufree(void *ptr)
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
  return 0;
}