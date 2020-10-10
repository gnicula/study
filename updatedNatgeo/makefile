CC=gcc
CFLAGS=-I. -Wall -std=c99

nationalb : main.o  readQuestionsFromFile.o shuffle.o sort.o display.o review.o
	$(CC) $(CFLAGS) -o nationalb main.o \
		readQuestionsFromFile.o \
		shuffle.o \
		sort.o \
		display.o \
		review.o

main.o : main.c questions.h
	$(CC) $(CFLAGS) -c main.c

readQuestionsFromFile.o : readQuestionsFromFile.c questions.h
	$(CC) $(CFLAGS) -c readQuestionsFromFile.c

shuffle.o : shuffle.c questions.h
	$(CC) $(CFLAGS) -c shuffle.c

sort.o : sort.c questions.h
	$(CC) $(CFLAGS) -c sort.c

display.o : display.c questions.h
	$(CC) $(CFLAGS) -c display.c

review.o : review.c questions.h
	$(CC) $(CFLAGS) -c review.c

clean :
	rm nationalb main.o \
		readQuestionsFromFile.o \
		shuffle.o \
		sort.o \
		display.o \
		review.o
