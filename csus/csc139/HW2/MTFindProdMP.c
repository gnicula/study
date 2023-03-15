/*
	Gabriele Nicula
	Section 01
    Extra Work: using multiple processes instead of multiple threads
    Compile with: g++ -O3 -Wall MPFindProd.c -o MPFindProd -lpthread

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
#include <semaphore.h>
#include <signal.h>
#include <sys/shm.h>
#include <sys/timeb.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#define MAX_SIZE 100000000
#define MAX_THREADS 16
#define RANDOM_SEED 9714
#define MAX_RANDOM_NUMBER 2500
#define NUM_LIMIT 9800

// Global variables
long gRefTime; //For timing
int gData[MAX_SIZE]; //The array that will hold the data

int gThreadCount; //Number of processes
// Place shared variables in shared memory
int* gShrDoneProcCount; //Number of processes that are done at a certain point. Whenever a process is done, it increments this. Used with the semaphore-based solution
int* gShrProcProd; //The modular product for each array division that a single process is responsible for
int* gShrProcDone; //Is this process done? Used when the parent is continually checking on child processes

// Process ids
int base_pid, status, pid;
int pids[MAX_THREADS];

// Semaphores - pointers to sem_t so we can place them in shared memory
sem_t* completed; //To notify parent that all processes have completed or one of them found a zero
sem_t* mutex; //Binary semaphore to protect the shared variable gDoneThreadCount

int SqFindProd(int size); //Sequential FindProduct (no threads) computes the product of all the elements in the array mod NUM_LIMIT
void ProcFindProd(int, int, int); //Multi Process FindProduct but without semaphores
void ProcFindProdWithSemaphore(int, int, int); //Multi Process FindProduct with semaphores
int ComputeTotalProduct(); // Multiply the division products to compute the total modular product 
void InitSharedVars();
void GenerateInput(int size, int indexForZero); //Generate the input array
void CalculateIndices(int arraySize, int thrdCnt, int indices[MAX_THREADS][3]); //Calculate the indices to divide the array into T divisions, one division per process
int GetRand(int min, int max);//Get a random number between min and max

//Timing functions
long GetMilliSecondTime(struct timeb timeBuf);
long GetCurrentTime(void);
void SetTime(void);
long GetTime(void);

int main(int argc, char *argv[]){

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

    base_pid = getpid();

    // Create shared memory for per process result with shmget() and attach with shmat().
    // Using shmget because child process created after the shared memory has been created 
    // and attached, the child process will receive the shared memory in its address space
    int shmidProd = shmget(IPC_PRIVATE, sizeof(int) * MAX_THREADS, IPC_CREAT | 0600);
    if (shmidProd < 0) {
        perror("shmidProd = shmget(IPC_PRIVATE, sizeof(int) * MAX_THREADS, IPC_CREAT | 0600)");
        exit(1);
    }
    gShrProcProd = (int*)shmat(shmidProd, NULL, 0);
    if (gShrProcProd == (int *)(-1)) {
        perror("shmat(shmidProd, NULL, 0)");
        exit(1);
    }
    int shmidDone = shmget(IPC_PRIVATE, sizeof(int) * MAX_THREADS, IPC_CREAT | 0600);
    if (shmidDone < 0) {
        perror("shmidDone = shmget(IPC_PRIVATE, sizeof(int) * MAX_THREADS, IPC_CREAT | 0600)");
        exit(1);
    }
    gShrProcDone = (int*)shmat(shmidDone, NULL, 0);
    if (gShrProcDone == (int *)(-1)) {
        perror("shmat(shmidDone, NULL, 0)");
        exit(1);
    }

	// Multiprocess with parent waiting for all child processes
	InitSharedVars();
	SetTime();

	// Write your code here
	// Fork processes, and then let the parent wait for all processes
	// The process start function is ThFindProd
	// Don't forget to properly initialize shared variables
	int th_ID = 0;
	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
        // fork new process
        pid = fork();
        if (pid == 0) {
            // child code path START
            ProcFindProd(indices[th_ID][0], indices[th_ID][1], indices[th_ID][2]);
            // Process did its job, exit
        	exit(0);
            // child code path END
        } else {
            // parent code path, store the child pid
            pids[th_ID] = pid;
        }
    }
	//sleep(0);

	// Now processes are running and we wait for all of them to complete.
	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
		wait(NULL);
	}
    // All child processes terminated their computation, compute final product
    prod = ComputeTotalProduct();
	printf("Multi process multiplication with parent waiting for all children completed in %ld ms. Product = %d\n", GetTime(), prod);

	// Multi-process with busy waiting (parent continually checking on child process without using semaphores)
	InitSharedVars();
	SetTime();

	// Write your code here
    // Don't use any semaphores in this part
	// Create processes with fork(), and then make the parent continually check on all child process
	// Don't forget to properly initialize shared variables

	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
        // fork new process
        pid = fork();
        if (pid == 0) {
            // child code path START
            ProcFindProd(indices[th_ID][0], indices[th_ID][1], indices[th_ID][2]);
            // Process did its job, exit
        	exit(0);
            // child code path END
        } else {
            // parent code path, store the child pid
            pids[th_ID] = pid;
        }
    }
	
	// make sure that the shared variables that the busy-waiting loop checks on 
    // are declared as volatile.
	volatile int numDone = 0;
	volatile bool seenZero = false;
	// Start a loop where we continuously check on two conditions
	// 1) Number of processes that finished
	// 2) A zero result from any of the processes
    // Both gShrProcProd and gShrProcDone are in shared memory between processes
	while (numDone != gThreadCount && !seenZero) {
		numDone = 0;
		for (i = 0; i < gThreadCount; ++i) {
			if (gShrProcProd[i] == 0) {
				seenZero = true;
				// printf("Child process pid: %d found a zero, sending signal to children.\n", pids[i]);
				// Break out of for loop and while() will stop because seenZero is true
				break;
			}
			if (gShrProcDone[i] == 1) {
				++numDone;
			}
		}
		// printf("numDone: %d\n", numDone);
	}
    // Send terminate signal to processes as there is no need to continue.
	// for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
    //     kill(pids[th_ID], SIGTERM);
	// }
	
    // Total product can be computed from shared memory
    prod = ComputeTotalProduct();
	printf("Multi process multiplication with parent continually checking on children completed in %ld ms. Product = %d\n", GetTime(), prod);

	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
        waitpid(pids[th_ID], &status, 0);
	}

	// Multi-process with semaphores
	InitSharedVars();
    // Initialize shared memory for processes done counting
    int shmidDoneCount = shmget(IPC_PRIVATE, sizeof(int), IPC_CREAT | 0600);
    if (shmidDoneCount < 0) {
        perror("shmidDoneCount = shmget(IPC_PRIVATE, sizeof(int), IPC_CREAT | 0600)");
        exit(1);
    }
    gShrDoneProcCount = (int*)shmat(shmidDoneCount, NULL, 0);
    if (gShrDoneProcCount == (int *)(-1)) {
        perror("shmat(shmidDoneCount, NULL, 0)");
        exit(1);
    }
    *gShrDoneProcCount = 0;

    // Initialize your semaphores here
    // Semaphores must be placed in shared memory so we need to allocate and attach
    int completed_id = shmget(IPC_PRIVATE, sizeof(sem_t), IPC_CREAT | 0600);
    if (completed_id < 0) {
        perror("completed_id = shmget(IPC_PRIVATE, sizeof(sem_t), IPC_CREAT | 0600)");
        exit(1);
    }
    completed = (sem_t*)shmat(completed_id, (void*)0, 0);
    if (completed == (sem_t *)(-1)) {
        perror("completed shmat()");
        exit(1);
    }
    int mutex_id = shmget(IPC_PRIVATE, sizeof(sem_t), IPC_CREAT | 0600);
    if (mutex_id < 0) {
        perror("mutrex_id = shmget(IPC_PRIVATE, sizeof(sem_t), IPC_CREAT | 0600)");
        exit(1);
    }
    mutex = (sem_t*)shmat(mutex_id, (void*)0, 0);
    if (mutex == (sem_t *)(-1)) {
        perror("mutex shmat()");
        exit(1);
    }

    // Second argument (pshared) is 1 to allow inter-process semaphore
	sem_init(completed, 1, 0);
	// Give an initial value of 1 so the first process can enter the critical section
	sem_init(mutex, 1, 1);

	SetTime();

    // Write your code here
	// Initialize processes, and then make the parent wait on the "completed" semaphore
	// The process function to execute is ProcFindProdWithSemaphore
	// Don't forget to properly initialize shared variables and semaphores using sem_init

	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
        // fork new process
        pid = fork();
        if (pid == 0) {
            // child code path START
            ProcFindProdWithSemaphore(indices[th_ID][0], indices[th_ID][1], indices[th_ID][2]);
            // Process did its job, exit
        	exit(0);
            // child code path END
        } else {
            // parent code path, store the child pid
            pids[th_ID] = pid;
        }
    }

	int err = sem_wait(completed);
	if (err == -1) {
        // This happened on MacOS with Apple M1 silicon
		printf("Could not wait on semaphore completed\n");
	}

    // Send terminate signal to child processes
	// for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
    //     kill(pids[th_ID], SIGTERM);
	// }

    // Total product can be computed from shared memory
    prod = ComputeTotalProduct();
	printf("Multi process multiplication with parent waiting on a semaphore completed in %ld ms. Product = %d\n", GetTime(), prod);

    // Wait for processes to exit
	for (th_ID = 0; th_ID < gThreadCount; ++th_ID) {
        waitpid(pids[th_ID], &status, 0);
	}

    // Detach and close shared memory in parent
    // Child processes only detach in their code paths.
    shmdt(gShrProcProd);
    shmctl(shmidProd, IPC_RMID, NULL);
    shmdt(gShrProcDone);
    shmctl(shmidDone, IPC_RMID, NULL);
    shmdt(gShrDoneProcCount);
    shmctl(shmidDoneCount, IPC_RMID, NULL);

    // Cleanup the semaphores and their shared memory
    sem_destroy(completed);
    sem_destroy(mutex);
    shmdt(completed);
    shmctl(completed_id, IPC_RMID, NULL);
    shmdt(mutex);
    shmctl(mutex_id, IPC_RMID, NULL);

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
void ProcFindProd(int procNum, int procIndexStart, int procIndexEnd) {
	int result = 1;
	int i = 0;
	// printf("proc: %d, start: %d, end: %d\n", procNum, procIndexStart, procIndexEnd);
	for (i = procIndexStart; i <= procIndexEnd; ++i) {
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
	gShrProcProd[procNum] = result;
	gShrProcDone[procNum] = 1;
    // printf("proc: %d, result: %d, shared: %d\n", procNum, result, gShrProcProd[procNum]);

    // Detach from shared memory objects
    shmdt(gShrProcProd);
    shmdt(gShrProcDone);
}

// Write a thread function that computes the product of all the elements in one division of the array mod NUM_LIMIT
// REMEMBER TO MOD BY NUM_LIMIT AFTER EACH MULTIPLICATION TO PREVENT YOUR PRODUCT VARIABLE FROM OVERFLOWING
// When it is done, this function should store the product in gThreadProd[threadNum]
// If the product value in this division is zero, this function should post the "completed" semaphore
// If the product value in this division is not zero, this function should increment gDoneThreadCount and
// post the "completed" semaphore if it is the last thread to be done
// Don't forget to protect access to gDoneThreadCount with the "mutex" semaphore

void ProcFindProdWithSemaphore(int procNum, int procIndexStart, int procIndexEnd) {
	int result = 1;
	int i = 0;
	// printf("semaphore proc: %d, start: %d, end: %d\n", procNum, procIndexStart, procIndexEnd);

	for (i = procIndexStart; i <= procIndexEnd; ++i) {
		// check if a zero is in the input and if so, set the process result to zero
		// and raise the semaphore so the parent process can compute the result
		if(gData[i] == 0) {
			result = 0;
			gShrProcProd[procNum] = 0;
			// printf("proc ID: %d saw a zero\n" , threadNum);
			sem_post(completed);
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
	gShrProcProd[procNum] = result;
	gShrProcDone[procNum] = 1;
	
	// Acquire the mutex and enter critical section to update gShrDoneProcCount
	sem_wait(mutex);
	// gShrDoneProcCount can be updated by only one process at a time
	*gShrDoneProcCount += 1;

	// printf("Process no: %d gShrDoneProcCount: %d\n" , procNum, *gShrDoneProcCount);

	// In the critical section we check if gDoneThreadCount reached the gThreadCount value
	// which would mean that all threads have finished and updated their result
	if (*gShrDoneProcCount == gThreadCount) {
		// printf("Process no: %d was the last one\n" , procNum);
		
		// All processes are done so we post that the computation is completed
		sem_post(completed);
	}
	// Release the mutex with sem_post() so other threads can enter their critical section
	sem_post(mutex);

    // Detach from shared memory objects	
    shmdt(gShrProcProd);
    shmdt(gShrProcDone);
    shmdt(gShrDoneProcCount);
	shmdt(completed);
    shmdt(mutex);
}


int ComputeTotalProduct() {
    int i, prod = 1;

	for(i=0; i<gThreadCount; i++)
	{
		// printf("gprocprod[%d] = %d\n" , i, gShrProcProd[i]);
		prod *= gShrProcProd[i];
		prod %= NUM_LIMIT;
		// printf("compute total loop: %d" , prod);
	}
	// printf("compute total product: %d" , prod);
	return prod;
}

void InitSharedVars() {
	int i;

	for(i=0; i<gThreadCount; i++){
		gShrProcDone[i] = 0;
		gShrProcProd[i] = 1;
        pids[i] = -1;
	}
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