/**
 * Symbolic calculator
 * 
 * This class should NOT be changed in any way
 * 
 */
import java.util.*;
import java.io.*;

public class Calculator {
  
  private Parser parser;
  private Stokenizer tokenizer;
  private Map<String, Sexpr> variables;  
  
  public Calculator(Stokenizer tokenizer) {
    this.tokenizer = tokenizer;
    variables = new TreeMap<String, Sexpr>();
    variables.put("PI", new Constant(Math.PI)); // Predefined constant
    variables.put("E", new Constant(Math.E));   // Predefined constant
    variables.put("ans", new Constant(0.));  
    parser = new Parser(tokenizer);    
  }
  
  public static void main(String[] args) throws IOException {
    System.out.println("Symbolic calculator version 2016-05-08");
    Stokenizer tokenizer = new Stokenizer();   
    Calculator calc = new Calculator(tokenizer);
    while (true) {
      try {
        calc.statement();
      } catch (SyntaxException syntaxError) {
        System.out.println("*** Syntax error: " + syntaxError.getMessage());
        System.out.println("*** The error occured at token '" + 
                           tokenizer.getToken() + "' just after token '" +
                           tokenizer.getPreviousToken() + "'");
        
        while (!tokenizer.isEOL())
          tokenizer.nextToken();
      } catch (EvaluationException evaluationException) {
        System.out.println("*** Evaluation error: " + evaluationException.getMessage()); 
      }
      
      while (!tokenizer.isEOL())
        tokenizer.nextToken();
    }
  }
  
  /**
   * Handles a statement. See syntax diagram!
   */
  public void statement() 
    throws IOException
  {
    do {                                                // Skip empty lines  
      System.out.print("Input : ");
      tokenizer.nextToken();
    } while (tokenizer.isEOL());
    
    String command = tokenizer.getToken();                // First token
    if (command.equals("quit") || tokenizer.isEOS()) {    // could be the quit command,
      System.out.println("Bye!");
      System.exit(0);
    } else if (command.equals("vars")) {                  // the vars command,
      for (Map.Entry<String, Sexpr> entry : variables.entrySet()) {
        System.out.println(entry.getKey()+ "\t : " + entry.getValue());
      }
      tokenizer.nextToken();  
    } else if (command.equals("file")) {
      tokenizer.nextToken();
      fileInput(tokenizer.getToken());
    } else {
      Sexpr parsed = parser.assignment();               // the first element in an expression.
      if (!tokenizer.isEOL()) {
        throw new SyntaxException("Expected EOL");
      }
      System.out.println("Parsed: " + parsed);          // For debugging purposes
      Sexpr value = parsed.eval(variables);
      variables.put("ans", value);
      System.out.println("Result: " + value.toString());
    }
    if (!tokenizer.isEOL()) {
      throw new SyntaxException("Expected EOL");
    }
  }
   
  /**
   * Takes the input to the calculator from a file
   * Note: The commands quit and file can not presently be used in a file
   * @param fileName name of the file to be used as input
   */
  public void fileInput(String fileName)
    throws IOException
  {
    Scanner scan = new Scanner(System.in);
    if (!tokenizer.isWord()) {
      throw new SyntaxException("Expected filename. Found: " + tokenizer.getToken());   
    }
    File input = new File(fileName);
    if (!input.exists()) {
      throw new EvaluationException("The file '" + fileName + "' does not exist");
    }
    System.out.println();
    tokenizer.nextToken();
    Stokenizer strTokenizer = null;
    Scanner fsc = new Scanner(input);
    while (fsc.hasNextLine()) {
      String line = fsc.nextLine();
      String comment = "";
      int commentIndex = line.indexOf("%");
      if (commentIndex>=0) {
        comment = line.substring(commentIndex);
        line = line.substring(0,commentIndex);
      }
      line = line.trim();
      if (line.length()==0 && comment.length()>0) {
        System.out.println(comment);
      } else if (line.length()>0) {
        try {
          System.out.format("Input  : %-35s  %s", line, comment);
          scan.nextLine();
          strTokenizer = new Stokenizer(line);
          Parser parser = new Parser(strTokenizer);
          strTokenizer.nextToken();
          if (strTokenizer.getToken().equals("vars")) {
            for (Map.Entry<String, Sexpr> entry : variables.entrySet()) {
              System.out.println(entry.getKey()+ "\t : " + entry.getValue());
            }   
          } else {
            Sexpr parsed = parser.assignment();
            System.out.println("Parsed : " + parsed);
            Sexpr value = parsed.eval(variables);
            System.out.format("%-46s  %s","Result : ", value); 
            variables.put("ans", value);
            scan.nextLine();
          }
        }         
        catch (SyntaxException syntaxError) {
          System.out.println("*** Syntax error: " + syntaxError.getMessage());
          System.out.println("*** The error occured at token '" + 
                             strTokenizer.getToken() + "' just after token '" +
                             strTokenizer.getPreviousToken() + "'");        
          //while (!tokenizer.isEOL())
          //  tokenizer.nextToken();
          scan.nextLine();
        } catch (EvaluationException evaluationException) {
          System.out.println("*** Evaluation error: " + evaluationException.getMessage() + "\n"); 
          scan.nextLine();
        }
      }
    }
    System.out.println("* End of file input *");
  }
}


