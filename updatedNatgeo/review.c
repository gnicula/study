#include <stdio.h>
#include "questions.h"

void ReviewQuestions(Questions_T questionList[], int count) 
{
	// show each and every question  and the response he provided.  
	// Show him the correct answer in case the answer was incorrect.
	
	printf("\n\n");
	for (int i = 0; i < count; ++i)
        {
                printf("Question %d: %s", questionList[i].qNum, questionList[i].q);
                printf("%s%s%s%s", questionList[i].c1, questionList[i].c2, questionList[i].c3, questionList[i].c4);
		if (questionList[i].answer[0] == questionList[i].response)
		{
			printf("Your answer %c was correct.\n", questionList[i].response);
		} else {
			printf("Your answer %c was incorrect, correct answer was %c.\n",
				questionList[i].response, questionList[i].answer[0]);
		}
	}
 	

}
