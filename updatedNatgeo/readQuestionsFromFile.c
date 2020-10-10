#include <stdio.h>
#include <stdlib.h>
#include "questions.h"


/*
 * This function returns the number Of Questions Read
*/
int readQuestionsFromFile(Questions_T questionList[], int count)
{

  	//open the file and read all questions into this array.
  	//count is the number of cells in the array
	
  	FILE *fp = fopen("/gaia/class/student/srivatss/IPC/ngbee/questionbank.txt", "rt");
  	if (fp == NULL)
  	{
     		printf("Error opening questionbank.txt file, make sure the path is full and correct!\n");
     		exit(1);
  	}

  	int i = 0;
  	while (i < count)
  	{
    		if (fgets(questionList[i].q, 300, fp) != NULL)
    		{
      			fgets(questionList[i].c1, 300, fp);
      			fgets(questionList[i].c2, 300, fp);
      			fgets(questionList[i].c3, 300, fp);
      			fgets(questionList[i].c4, 300, fp);
      			fgets(questionList[i].answer, 3, fp);

			questionList[i].qNum = i+1;
			++i;
    		} else
		{
      			break;
    		}
  	}
  	return i;
}
