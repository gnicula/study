#include <stdio.h>
#include "questions.h"

//Sort the first questions.  

int sort(Questions_T questionList[], int count)
{
	for (int i = 1; i < count; ++i)
	{
		Questions_T currentQ = questionList[i];
		int j = i - 1;
		for (; j >= 0 && questionList[j].qNum > currentQ.qNum; --j)
		{
			questionList[j+1] = questionList[j];
		}
		
		 questionList[j + 1] = currentQ;
	}
  
  return count;
}
