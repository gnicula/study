#include <stdio.h>

#include "questions.h"

int main( )
{
    	Questions_T questionList[30];

	int num_questions = readQuestionsFromFile(questionList, 30);
    	printf("Read %d questions.\n", num_questions);

    	shuffle(questionList, 30);

	sort(questionList, 5);

	DisplayQuestions(questionList, 5);

	ReviewQuestions(questionList, 5);

    	return 0;
}
