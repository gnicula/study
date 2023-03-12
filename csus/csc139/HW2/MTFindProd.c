/*
	Gabriele Nicula
	Section 01

	TESTED ON: 
		a) ecs lab computer, Linux ecs-pa-coding3.ecs.csus.edu, Intel(R) Xeon(R) Gold 6254 CPU, RHELL 7.9, gcc 4.8.5

		-bash-4.2$ lscpu
		Architecture:          x86_64
		CPU op-mode(s):        32-bit, 64-bit
		Byte Order:            Little Endian
		CPU(s):                4
		On-line CPU(s) list:   0-3
		Thread(s) per core:    1
		Core(s) per socket:    2
		Socket(s):             2
		NUMA node(s):          1
		Vendor ID:             GenuineIntel
		CPU family:            6
		Model:                 58
		Model name:            Intel(R) Xeon(R) Gold 6254 CPU @ 3.10GHz
		Stepping:              0
		CPU MHz:               3092.734
		BogoMIPS:              6185.46
		Hypervisor vendor:     VMware
		Virtualization type:   full
		L1d cache:             32K
		L1i cache:             32K
		L2 cache:              1024K
		L3 cache:              25344K
		NUMA node0 CPU(s):     0-3

*/

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/timeb.h>
#include <semaphore.h>

#define MAX_SIZE 100000000
#define MAX_THREADS 16
#define RANDOM_SEED 9714
#define MAX_RANDOM_NUMBER 2500
#define NUM_LIMIT 9800

// Global variables
long gRefTime; //For timing
int gData[MAX_SIZE]; //The array that will hold the data

int gThreadCount; //Number of threads
int gDoneThreadCount; //Number of threads that are done at a certain point. Whenever a thread is done, it increments this. Used with the semaphore-based solution
int gThreadProd[MAX_THREADS]; //The modular product for each array division that a single thread is responsible for
bool gThreadDone[MAX_THREADS]; //Is this thread done? Used when the parent is continually checking on child threads

// Semaphores
sem_t completed; //To notify parent that all threads have completed or one of them found a zero
sem_t mutex; //Binary semaphore to protect the shared variable gDoneThreadCount

int SqFindProd(int size); //Sequential FindProduct (no threads) computes the product of all the elements in the array mod NUM_LIMIT
void *ThFindProd(void *param); //Thread FindProduct but without semaphores
void *ThFindProdWithSemaphore(void *param); //Thread FindProduct with semaphores
int ComputeTotalProduct(); // Multiply the division products to compute the total modular product 
void InitSharedVars();
void GenerateInput(int size, int indexForZero); //Generate the input array
void CalculateIndices(int arraySize, int thrdCnt, int indices[MAX_THREADS][3]); //Calculate the indices to divide the array into T divisions, one division per thread
int GetRand(int min, int max);//Get a random number between min and max

//Timing functions
long GetMilliSecondTime(struct timeb timeBuf);
long GetCurrentTime(void);
void SetTime(void);
long GetTime(void);

int main(int argc, char *argv[]){

	pthread_t tid[MAX_THREADS];
	pthread_attr_t attr[MAX_THREADS];
	int indices[MAX_THREADS][3];
	int i, indexForZero, arraySize, prod;

	// Code for parsing and checking command-line arguments
	if(argc != 4){
		fprintf(stderr, "Invalid number of arguments!\n");
		exit(-1);
	}
	if((arraySize = atoi(argv[1])) <= 0 || arraySize > MAX_SIZE){
		fprintf(stderr, "Invalid Array Size\n");
		exit(-1);
	}
	gThreadCount = atoi(argv[2]);
	if(gThreadCount > MAX_THREADS || gThreadCount <=0){
		fprintf(stderr, "Invalid Thread Count\n");
		exit(-1);
	}
	indexForZero = atoi(argv[3]);
	if(indexForZero < -1 || indexForZero >= arraySize){
		fprintf(stderr, "Invalid index for zero!\n");
		exit(-1);
	}

    GenerateInput(arraySize, indexForZero);
	// for (int i = 0; i < arraySize; ++i ) {
	// 	printf("%d ", gData[i]);
	// }

    CalculateIndices(arraySize, gThreadCount, indices);
	// for (int i = 0; i < gThreadCount; ++i ) {
	// 	printf("%d %d %d \n", indices[i][0], indices[i][1], indices[i][2]);
	// }

	// Code for the sequential part
	SetTime();
	prod = SqFindProd(arraySize);
	printf("Sequential multiplication completed in %ld ms. Product = %d\n", GetTime(), prod);

	// Threaded with parent waiting for all child threads
	InitSharedVars();
	SetTime();

	// Write your code here
	// Initialize threads, create threads, and then let the parent wait for all threads using pthread_join
	// The thread start function is ThFindProd
	// Don't forget to properly initialize shared variables
	int th_ID = 0;
	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
		int error = pthread_create(&tid[th_ID], NULL, ThFindProd, &indices[th_ID]);
		if (error != 0) {
			printf("Could not create thread: %d, error: %d\n", th_ID, error);
		}
	}
	//sleep(0);

	// Now threads are running and we wait for all of them to complete.
	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
		pthread_join(tid[th_ID], NULL);
	}

    prod = ComputeTotalProduct();
	printf("Threaded multiplication with parent waiting for all children completed in %ld ms. Product = %d\n", GetTime(), prod);

	// Multi-threaded with busy waiting (parent continually checking on child threads without using semaphores)
	InitSharedVars();
	SetTime();

	// Write your code here
    // Don't use any semaphores in this part
	// Initialize threads, create threads, and then make the parent continually check on all child threads
	// The thread start function is ThFindProd
	// Don't forget to properly initialize shared variables

	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
		int error = pthread_create(&tid[th_ID], NULL, ThFindProd, &indices[th_ID]);
		if (error != 0) {
			printf("Could not create thread: %d, error: %d\n", th_ID, error);
		}
	}
	
	// make sure that the shared variables that the busy-waiting loop checks on are declared as volatile.
	volatile int numDone = 0;
	volatile bool seenZero = false;
	// Start a loop where we continuously check on two conditions
	// 1) Number of threads that finished
	// 2) A zero result from any of the threads
	while (numDone != gThreadCount && !seenZero) {
		numDone = 0;
		for (i = 0; i < gThreadCount; ++i) {
			if (gThreadProd[i] == 0) {
				seenZero = true;
				// printf("found a zero, index: %d", i);
				// Break out for loop and while will stop because seenZero is true
				break;
			}
			if (gThreadDone[i]) {
				++numDone;
			}
		}
		// printf("numDone: %d\n", numDone);
	}
	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
		pthread_cancel(tid[th_ID]);
	}
	
    prod = ComputeTotalProduct();
	printf("Threaded multiplication with parent continually checking on children completed in %ld ms. Product = %d\n", GetTime(), prod);

	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
		pthread_join(tid[th_ID], NULL);
	}

	// Multi-threaded with semaphores

	InitSharedVars();
    // Initialize your semaphores here
	sem_init(&completed, 0, 0);
	// Give an initial value of 1 so the first thread can enter the critical section
	sem_init(&mutex, 0, 1);

	SetTime();

    // Write your code here
	// Initialize threads, create threads, and then make the parent wait on the "completed" semaphore
	// The thread start function is ThFindProdWithSemaphore
	// Don't forget to properly initialize shared variables and semaphores using sem_init

	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
		// printf("Starting thread: %d\n", th_ID);
		int error = pthread_create(&tid[th_ID], NULL, ThFindProdWithSemaphore, &indices[th_ID]);
		if (error != 0) {
			printf("Could not create thread: %d, error: %d\n", th_ID, error);
		}
	}

	int err = sem_wait(&completed);
	if (err == -1) {
		printf("Could not wait on semaphore completed\n");
	}
	//printf("Main thread gDoneThreadCount: %d\n" , gDoneThreadCount);
	
	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
		pthread_cancel(tid[th_ID]);
	}
	
    prod = ComputeTotalProduct();
	printf("Threaded multiplication with parent waiting on a semaphore completed in %ld ms. Product = %d\n", GetTime(), prod);

	// for (int th_ID = 0; th_ID < gThreadCount; ++th_ID) {
	// 	pthread_join(tid[th_ID], NULL);
	// }
}

// Write a regular sequential function to multiply all the elements in gData mod NUM_LIMIT
// REMEMBER TO MOD BY NUM_LIMIT AFTER EACH MULTIPLICATION TO PREVENT YOUR PRODUCT VARIABLE FROM OVERFLOWING
int SqFindProd(int size) {
	int result = 1;
	int i = 0;

	for (i = 0; i < size; ++i) {
		if(gData[i] == 0) {
			result = 0;
			break;
		}
		result *= gData[i];
		result %= NUM_LIMIT;
		// We could also take advantage of result being zero because the product can be a multiple of NUM_LIMIT
		// We could also adjust the result to be NUM_LIMIT in this situation
		// TODO: Ask about these cases in class

		// if (result == 0) {
		// 	break;
		// }
		// if (result == 0) {
		// 	result = NUM_LIMIT;
		// }
	}
    
	return result;
}

// Write a thread function that computes the product of all the elements in one division of the array mod NUM_LIMIT
// REMEMBER TO MOD BY NUM_LIMIT AFTER EACH MULTIPLICATION TO PREVENT YOUR PRODUCT VARIABLE FROM OVERFLOWING
// When it is done, this function should store the product in gThreadProd[threadNum] and set gThreadDone[threadNum] to true
void* ThFindProd(void *param) {
	int threadNum = ((int*)param)[0];
	int threadIndxStart = ((int*)param)[1];
	int threadIndxEnd = ((int*)param)[2];
	int result = 1;
	int i = 0;
	// printf("thread: %d, start: %d, end: %d\n", threadNum, threadIndxStart, threadIndxEnd);
	for (i = threadIndxStart; i <= threadIndxEnd; ++i) {
		if(gData[i] == 0) {
			result = 0;
			break;
		} else {
			result *= gData[i];
			// if (result == 0) {
			// 	break;
			// }
			result %= NUM_LIMIT;
			// if (result == 0) {
			// 	result = NUM_LIMIT;
			// }
		}
	}
	gThreadProd[threadNum] = result;
	gThreadDone[threadNum] = true;
	pthread_exit(NULL);

}

// Write a thread function that computes the product of all the elements in one division of the array mod NUM_LIMIT
// REMEMBER TO MOD BY NUM_LIMIT AFTER EACH MULTIPLICATION TO PREVENT YOUR PRODUCT VARIABLE FROM OVERFLOWING
// When it is done, this function should store the product in gThreadProd[threadNum]
// If the product value in this division is zero, this function should post the "completed" semaphore
// If the product value in this division is not zero, this function should increment gDoneThreadCount and
// post the "completed" semaphore if it is the last thread to be done
// Don't forget to protect access to gDoneThreadCount with the "mutex" semaphore
void* ThFindProdWithSemaphore(void *param) {
	int threadNum = ((int*)param)[0];
	int threadIndxStart = ((int*)param)[1];
	int threadIndxEnd = ((int*)param)[2];
	int result = 1;
	int i = 0;

	// printf("thread: %d, start: %d, end: %d\n", threadNum, threadIndxStart, threadIndxEnd);
	for (i = threadIndxStart; i <= threadIndxEnd; ++i) {
		// check if a zero is in the input and if so, set the thread result to zero
		// and raise the semaphore so the parent thread can compute the result
		if(gData[i] == 0) {
			result = 0;
			gThreadProd[threadNum] = 0;
			// printf("thread ID: %d saw a zero\n" , threadNum);
			sem_post(&completed);
			break;
		} else {
			result *= gData[i];
			// if (result == 0) {
			// 	break;
			// }
			result %= NUM_LIMIT;
			// if (result == 0) {
			// 	result = NUM_LIMIT;
			// }
		}
	}
	gThreadProd[threadNum] = result;
	gThreadDone[threadNum] = true;
	
	// Acquire the mutex and enter critical section to update gDoneThreadCount
	sem_wait(&mutex);
	// gDoneThreadCount can be updated by only one thread at a time
	++gDoneThreadCount;

	// printf("thread ID: %d gDoneThreadCount: %d\n" , threadNum, gDoneThreadCount);

	// In the critical section we check if gDoneThreadCount reached the gThreadCount value
	// which would mean that all threads have finished and updated their result
	if (gDoneThreadCount == gThreadCount) {
		// printf("thread ID: %d was the last one\n" , threadNum);
		
		// All threads done so we post that the computation is completed
		sem_post(&completed);
	}
	// Release the mutex with sem_post() so other threads can enter their critical section
	sem_post(&mutex);
	
	pthread_exit(NULL);
}

int ComputeTotalProduct() {
    int i, prod = 1;

	for(i=0; i<gThreadCount; i++)
	{
		// printf("gthreadprod[%d] = %d\n" , i, gThreadProd[i]);
		prod *= gThreadProd[i];
		prod %= NUM_LIMIT;
		// printf("compute total loop: %d" , prod);
	}
	// printf("compute total product: %d" , prod);
	return prod;
}

void InitSharedVars() {
	int i;

	for(i=0; i<gThreadCount; i++){
		gThreadDone[i] = false;
		gThreadProd[i] = 1;
	}
	gDoneThreadCount = 0;
}

// Write a function that fills the gData array with random numbers between 1 and MAX_RANDOM_NUMBER
// If indexForZero is valid and non-negative, set the value at that index to zero
void GenerateInput(int size, int indexForZero) {
	int i = 0;
	for (i = 0; i < size; ++i) {
		gData[i] = GetRand(1, MAX_RANDOM_NUMBER);
	}
	if (0 <= indexForZero && indexForZero < size) {
		gData[indexForZero] = 0;
	}

}

// Write a function that calculates the right indices to divide the array into thrdCnt equal divisions
// For each division i, indices[i][0] should be set to the division number i,
// indices[i][1] should be set to the start index, and indices[i][2] should be set to the end index
void CalculateIndices(int arraySize, int thrdCnt, int indices[MAX_THREADS][3]) {
	int threadSize = arraySize / thrdCnt;
	int i = 0;

	if (arraySize % thrdCnt > 0) {
		threadSize += 1;
	}

	for (i = 0; i < thrdCnt; ++i) {
		indices[i][0] = i;
		indices[i][1] = i * threadSize;
		if ((i+1) * threadSize-1 >= arraySize) {
			// Adjusts end for the last thread to arraySize.
			indices[i][2] = arraySize - 1;
		} else {
			indices[i][2] = (i+1) * threadSize - 1;
		}
	}
}

// Get a random number in the range [x, y]
int GetRand(int x, int y) {
    int r = rand();
    r = x + r % (y-x+1);
    return r;
}

long GetMilliSecondTime(struct timeb timeBuf){
	long mliScndTime;
	mliScndTime = timeBuf.time;
	mliScndTime *= 1000;
	mliScndTime += timeBuf.millitm;
	return mliScndTime;
}

long GetCurrentTime(void){
	long crntTime=0;
	struct timeb timeBuf;
	ftime(&timeBuf);
	crntTime = GetMilliSecondTime(timeBuf);
	return crntTime;
}

void SetTime(void){
	gRefTime = GetCurrentTime();
}

long GetTime(void){
	long crntTime = GetCurrentTime();
	return (crntTime - gRefTime);
}

