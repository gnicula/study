#include <stdlib.h>
#include <time.h>

#include "questions.h"

int shuffle(Questions_T questionList[], int count)
{
  	//shuffle the array of structures.  
  	//count is the number of cells in the array.

  	srand(time(NULL));
  	for (int i = count-1; i > 0; --i) 
  	{
    		int j = rand() % (i+1);
    		Questions_T tmp = questionList[i];
    		questionList[i] = questionList[j];
    		questionList[j] = tmp;
  	}

 	return count;
}
