/*
  I used the url.g4 grammar from ANTLR4 grammars github which is intended to parse valid URLs.
  For the input2.txt which is the failing case I have introduces a small change, basically adding
  a single '?' character in the middle of a valid path component.
  This induces a parsing error because the parser tries to match the part after the introduced '?'
  as a 'query'. It matches partially but it misses an '=' or a '&' or an earlier EOF.
  The input2.txt should not be accepted because the part after '?' is not a valid 'query' according
  to the grammar rules.
  I have attached the parsing tree inspector for this input2.txt that shows the partial match and
  the error point in the input (input2_graph.jpg).
*/

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class Recognizer
{
	public static void main(String[] args) throws Exception
	{	String inputFile1 = "input1.txt";
		String inputFile2 = "input2.txt";

		// Read first input file.
		InputStream is1 = new FileInputStream(inputFile1);
		CharStream cs1 = CharStreams.fromStream(is1);

		// Instantiate a URL lexer and read the character stream.
		urlLexer lexer1 = new urlLexer(cs1);
		CommonTokenStream tokens1 = new CommonTokenStream(lexer1);
		tokens1.fill();

		// Instantiate a URL parser to parse the token stream.
		urlParser parser1 = new urlParser(tokens1);

		// BailErrorStrategy exception is thrown to cancel a parsing operation.
		// I use it to check if the input was fully parsed. 
		parser1.setErrorHandler(new BailErrorStrategy());
		
		try{
			ParseTree tree1 = parser1.url();
			System.out.println("input1.txt: Passed");
		} catch (ParseCancellationException e)
		{
			System.out.println("input1.txt: Failed");
		}

		 // Read second input file.
                InputStream is2 = new FileInputStream(inputFile2);
                CharStream cs2 = CharStreams.fromStream(is2);
                
                // Instantiate a URL lexer and read the character stream.
                urlLexer lexer2 = new urlLexer(cs2);
                CommonTokenStream tokens2 = new CommonTokenStream(lexer2);
                tokens2.fill();
                
                // Instantiate a URL parser to parse the token stream.
                urlParser parser2 = new urlParser(tokens2);
		parser2.setErrorHandler(new BailErrorStrategy());
               
		try {
			ParseTree tree2 = parser2.url();
                	System.out.println("input2.txt: Passed");
		} catch (ParseCancellationException e) 
		{ 
			System.out.println("input2.txt: Failed");
		} 
 	}
}
