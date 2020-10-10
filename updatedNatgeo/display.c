#include <stdio.h>
#include "questions.h"

void DisplayQuestions (Questions_T questionList[], int count) 
{
	// Display the questions and get the answer also from the user
	// save the user typed answer in the response field for each question

	char currentAns[3];	

	for (int i = 0; i < count; ++i)
	{
		printf("Question %d: %s", questionList[i].qNum, questionList[i].q);
        	printf("%s%s%s%s", questionList[i].c1, questionList[i].c2, questionList[i].c3, questionList[i].c4);
        	printf("Please enter 1, 2, 3, or 4:\n");
        	scanf("%s", &currentAns);
		
		questionList[i].response = currentAns[0];	
	}
}
