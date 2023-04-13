#!/bin/sh

javac -Xlint CPUAssignment.java

for i in `seq 1 16`
do
    rm input.txt
    cp "test_cases/input$i.txt" input.txt
    java CPUAssignment
    diff output.txt "test_cases/output$i.txt"
    error=$?
    if [[ $error -eq 1 ]]
    then
        echo "output.txt and test_cases/output$i.txt differ"
        cp "test_cases/input$i.txt" input_fail.txt
    fi
done
