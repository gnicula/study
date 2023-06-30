/*
 * Name: Gabriele Nicula
 * Class: CSC135-02
 * I used the calculator.g4 grammar from ANTLR4 grammar's github.
 * This is used to parse calculator equations of the form
 * <expression relop expression EOF>.
 * Valid input file: 
 * x = cos(pi * 3) EOF
 * Invalid input file: 
 * x = 10
 * y = x = z? EOF
 * This is an invalid input because the second line contains
 * more than one equal sign.
 * 
 * Steps to run:
 * generate lexer and parser: <java -jar antlr-4.13.0-complete.jar -no-listener calculator.g4>
 * compile with: <javac -cp ".:antlr-4.13.0-complete.jar" Recognizer.java>
 * run with: <java -cp ".:antlr-4.13.0-complete.jar" Recognizer "input1.txt" "input2.txt">
 * 
 * PROGRAM OUTPUT: 
 * The program assumes input1.txt and input2.txt are in the same folder as the 'Recognizer.java' 
 * and it will print 'Passed' or 'Failed' for each of them, to the console. 
 * FOR ANY ISSUES:
 * Please email gnicula@csus.edu  
 */

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class Recognizer {
  public static void main(String[] args) throws Exception {
    String inputFile1 = "input1.txt";
    String inputFile2 = "input2.txt";

    // Read first input file.
    InputStream is1 = new FileInputStream(inputFile1);
    CharStream cs1 = CharStreams.fromStream(is1);

    // Instantiate a calculator lexer and read the character stream.
    calculatorLexer lexer1 = new calculatorLexer(cs1);
    CommonTokenStream tokens1 = new CommonTokenStream(lexer1);
    tokens1.fill();

    // Instantiate a calculator parser to parse the token stream.
    calculatorParser parser1 = new calculatorParser(tokens1);

    // BailErrorStrategy exception is thrown to cancel a parsing operation.
    // I use it to check if the input was fully parsed.
    parser1.setErrorHandler(new BailErrorStrategy());

    try {
      ParseTree tree1 = parser1.equation();
      System.out.println("input1.txt: Passed");
    } catch (ParseCancellationException e) {
      System.out.println("input1.txt: Failed");
    }

    // Read second input file.
    InputStream is2 = new FileInputStream(inputFile2);
    CharStream cs2 = CharStreams.fromStream(is2);

    // Instantiate a calculator lexer and read the character stream.
    calculatorLexer lexer2 = new calculatorLexer(cs2);
    CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
    tokens2.fill();

    // Instantiate a calculator parser to parse the token stream.
    calculatorParser parser2 = new calculatorParser(tokens2);
    parser2.setErrorHandler(new BailErrorStrategy());

    try {
      ParseTree tree2 = parser2.equation();
      System.out.println("input2.txt: Passed");
    } catch (ParseCancellationException e) {
      System.out.println("input2.txt: Failed");
    }
  }
}
