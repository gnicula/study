#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <unistd.h>

#include "umem.h"

size_t const MARKER_VALUE = 0x12345678;
void *MARKER = (void*)MARKER_VALUE;

typedef struct node_t
{
  unsigned int size;
  struct node_t *next;
} node_t;

int g_allocationAlgo = 0;

node_t *g_head = (node_t *)NULL;

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
  void *ptr = mmap(NULL, sizeOfRegion, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANON, -1, 0);
  if (ptr == MAP_FAILED)
  {
    perror("mmap");
    exit(1);
  }
  printf("Node Size: %zu\n", nodeSize);
  g_head = (node_t *)ptr;
  g_head->next = NULL;
  g_head->size = sizeOfRegion - nodeSize;
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

void *mallocBestFit(size_t size)
{
  node_t *curBestFit = NULL;
  node_t *ptr = g_head;
  int adjustedSize = roundup(size);
  while (ptr != NULL)
  {
    if (ptr->size >= adjustedSize)
    {
      if (curBestFit == NULL)
      {
        curBestFit = ptr;
      }
      else if (curBestFit->size > ptr->size)
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
  size_t spaceLeft = curBestFit->size - adjustedSize;
  if (spaceLeft >= nodeSize + 8)
  {
    node_t *newNode = (node_t *)((char *)curBestFit + adjustedSize + nodeSize);
    newNode->size = spaceLeft - nodeSize;
    newNode->next = curBestFit->next;
    // TODO: Check if we are allowed to use 2 pointers in our node structure.
    if (curBestFit == g_head)
    {
      g_head = newNode;
    }
    else
    {
      for (node_t *p = g_head; p != NULL; p = p->next)
      {
        if (p->next == curBestFit)
        {
          p->next = newNode;
          break;
        }
      }
    }
  }
  else
  {
    if (curBestFit == g_head)
    {
      g_head = NULL;
    }
    else
    {
      for (node_t *p = g_head; p != NULL; p = p->next)
      {
        if (p->next == curBestFit)
        {
          p->next = curBestFit->next;
          break;
        }
      }
    }
  }
  curBestFit->size = adjustedSize;
  curBestFit->next = MARKER;
  return (void *)((char *)curBestFit + nodeSize);
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
  default:
    printf("Not implemented.");
  }
  return NULL;
}

void coalesce()
{
  node_t *ptr = g_head;
  char *end = NULL;
  while (ptr != NULL)
  {
    end = (char *)ptr + nodeSize + ptr->size;
    // printf("Head: %p\n", g_head);
    node_t *ptrEnd = (node_t *)(end);
    node_t *cmp = ptr->next;
    while (cmp != NULL)
    {
      // printf("ptr: %p, cmp: %p, ptrEnd: %p\n", ptr, cmp, ptrEnd);
      if (cmp == ptrEnd)
      {
        // printf("Entered Coalesce if\n");
        ptr->size = ptr->size + ptrEnd->size + nodeSize;
        ptr->next = ptrEnd->next;
      }
      cmp = cmp->next;
    }
    ptr = ptr->next;
  }
}

int ufree(void *ptr)
{
  node_t *nodePtr = (node_t *)((char *)ptr - nodeSize);
  if (nodePtr->next != MARKER)
  {
    return -1;
  }
  nodePtr->next = g_head;
  g_head = nodePtr;
  coalesce();
  return 0;
}