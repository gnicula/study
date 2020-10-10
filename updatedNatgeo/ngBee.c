#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() 
{
	int question_numbers[30];
	for (int i = 0; i < 30; ++i)
	{
    		question_numbers[i] = i;
	}

	/* shuffle the questions */
  	srand(time(NULL));
  	for (int i = 29; i > 0; --i) 
	{
    		int j = rand() % (i+1);
    		int tmp = question_numbers[i];
    		question_numbers[i] = question_numbers[j];
    		question_numbers[j] = tmp;
	}

  	/* extract the first five */
  	int selected_questions[5];
  	
	for (int i = 0; i < 5; ++i) 
	{
    		selected_questions[i] = question_numbers[i];
	}

	/* insertion sort the selected questions. */
  	for (int i = 1; i < 5; ++i) 
	{
    		int current = selected_questions[i];
    		int j = i - 1;
    		for (; j >= 0 && selected_questions[j] > current; --j) 
		{
      	
			selected_questions[j + 1] = selected_questions[j];
		}
  
		selected_questions[j + 1] = current;
  	}

  	printf("Welcome to NatGeo Trivia. Randomly drawing questions: ");
  	for (int i = 0; i < 5; ++i) 
	{
    		printf("%d ", selected_questions[i]);
  	}

  	printf("\n\n");

  	/* Open the file for reading with fopen */
  	FILE *fp = fopen("/gaia/class/student/srivatss/IPC/ngbee/questionbank.txt" , "r");
  	if(fp == NULL) 
	{
    		printf("Error opening questionbank.txt file, make sure the path is full and correct!\n");
    		return(-1);
  	}

  	/* Start reading questions from file.
     	If question number is in selected questions then display it and ask for an answer,
     	otherwise increment the current_q and read the next question.
     	I'm assuming the file ends with the last question with no lines after that.
   	*/

  	int total_correct_answers = 0;  // accumulate correct answers.
  	int current_q = 0;              // which question are we reading from file.
  	int current_selected_index = 0; // which question are we looking for.

  	/* loop until end of file when fgets returns NULL for a question line. */
  	while (1) 
	{
    		char line[200];

    		/* read the question */
    		if (fgets(line, 200, fp) != NULL) 
		{
      			/* we have a question, now read 4 option answers and correct answer.  */
      			char ans1[100], ans2[100], ans3[100], ans4[100];
      			int correct_answer = -1;
      			fgets (ans1, 100, fp);
      			fgets (ans2, 100, fp);
      			fgets (ans3, 100, fp);
      			fgets (ans4, 100, fp);
      			fscanf(fp, "%d\n", &correct_answer);

      			/* check if the question was selected. */
      			// printf("spoiler %s, correct: %d\n", line, correct_answer);
      			if (current_q == selected_questions[current_selected_index]) 
			{
        			int answer = 0;
        			/* fgets includes the newline characters in the read string so
         			* we don't need to add \n for print.*/
        
				printf("Question %d: %s", current_selected_index+1, line);
        			printf("%s%s%s%s", ans1, ans2, ans3, ans4);
        			printf("Please enter 1, 2, 3, or 4:\n");
        			
				scanf("%d", &answer);
        			
				if (answer == correct_answer) 
				{
          				++total_correct_answers;
          				printf("Correct. Your current score is %d\n\n", total_correct_answers);
        			} else {
          				printf("Sorry, incorrect. The correct answer is %d\n\n", correct_answer);
        			}
        
				++current_selected_index;
        			if (current_selected_index >= 5) 
				{
          				printf("Thank you for taking the quiz, you scored %d.\n", total_correct_answers);
          				break;
        			}
      			}
      
			++current_q;
      			// printf("current question index: %d, looking for %d\n", current_q, selected_questions[current_selected_index]);
    		} else {
      			printf("Unexpected EOF, question not found: %d\n", selected_questions[current_selected_index]);
      			break;
    		} 
  	}

  	return 0;
}

