/**
 * Parser
 * See syntax diagrams for documentation
 */
import java.util.Map;
import java.util.TreeSet;
public class Parser {
    
    private Stokenizer tokenizer;
    private TreeSet<String> functions;  // Names of recognized functions
    
    /**
     * Constructs a parser
     * @param tokenizer an initialized tokenizer
     */
    public Parser(Stokenizer tokenizer) {  
        this.tokenizer = tokenizer; 
        // Create a set with the names of the recognized functions
        functions = new TreeSet<String>();
        functions.add("sin");
        functions.add("cos");
        functions.add("exp");
        functions.add("log");
    }
    
    /**
     * Handles an assignment
     */
    public Sexpr assignment() {
        Sexpr value = expression();
        while (tokenizer.getChar() == '=') {
            tokenizer.nextToken();
            if (tokenizer.isWord()) {
                value = new Assignment(value,
                                       new Variable(tokenizer.getWord()));
                tokenizer.nextToken();
            } else {
                throw new SyntaxException("Expected variable after '='");
            }
        }
        
        return value;
    }
    
    /**
     * Handles a sum of terms 
     */
    public Sexpr expression() {
        Sexpr sum = term();
        int c;
        while ( ((c=tokenizer.getChar()) == '+') ||
               ((c=tokenizer.getChar()) == '-') ) {
            tokenizer.nextToken();
            if (c=='+') {
                sum = new Addition(sum, term());
            }
            else {
                sum = new Subtraction(sum, term());
            }
        }
        return sum;
    }
    
    /**
     * Handles a product of factors
     */
    public Sexpr term() {
        int c;
        Sexpr prod = factor();
        while ( ((c=tokenizer.getChar()) == '*') ||
                ((c=tokenizer.getChar()) == '/')) {
            tokenizer.nextToken();
            if (c=='*') {
                prod = new Multiplication(prod, factor());
            }
            else {
                Sexpr denom = factor();
                prod = new Division(prod, denom);
            }
        }
        return prod;
    }
    
    /**
     * Handles a differentiation:
     * <primary>[' <variable> [' <variable> ...]]
     */
    public Sexpr factor() {
        Sexpr result = primary(); // don't call variable 'result'
        while (tokenizer.getChar() == '\'') {
            
            tokenizer.nextToken();
            if (tokenizer.isWord()) {
                result = new Differentiation(result,
                                             new Variable(tokenizer.getWord()));
                tokenizer.nextToken();
            }
            else throw new SyntaxException("Expected variable after apostrophe");
        }
        return result;
    }
    
    /**
     * Handles a primary  (see syntax diagram)
     */
    public Sexpr primary() {
        Sexpr result = null;
        if (tokenizer.getChar() == '(') {          // Parentheses
            tokenizer.nextToken(); 
            result = assignment();
            if (tokenizer.getChar() == ')') {
                tokenizer.nextToken();
            } else {
                throw new SyntaxException("Expected ')'");
            }
        } 
        
        // Unary minus
        else if (tokenizer.getChar() == '-') {
            tokenizer.nextToken();
            result = new Negation(primary());  
        } 
        
        // Quotation
        else if (tokenizer.getChar() == '"') {
            tokenizer.nextToken();
            result = new Quotation(primary());  
        } 
        
        // Evaluation
        else if (tokenizer.getChar() == '&') {
            tokenizer.nextToken();
            result = primary();
            result = new Evaluation(result);
        }
        
        else if (tokenizer.isNumber()) {           // Number
            result = new Constant(tokenizer.getNumber());
            tokenizer.nextToken();
        } 
        
        else if (tokenizer.isWord()) {             // Word
            String theWord = tokenizer.getWord();
            tokenizer.nextToken();
            if (functions.contains(theWord)) {           // Function?
                result = function(theWord);
            } else  {
                result = new Variable(theWord);            // Variable
            } 
        } 
        
        else {
            throw new SyntaxException("Expected number, word, '-', " + 
                                      "'\"', '&' or '('");
        }
        
        return result;
    }
    
    public Sexpr function(String functionName) {
        
        Sexpr operand;
        if (tokenizer.getChar()=='(') {
            operand = primary();
        }
        else
            throw new SyntaxException("Expected '(' after '"+functionName+"'");
        
        if (functionName.equals("sin"))
            return new Sin(operand);
        else if (functionName.equals("cos"))
            return new Cos(operand);
        else if (functionName.equals("exp"))
            return new Exp(operand);
        else if (functionName.equals("log"))
            return new Log(operand);
        else
            throw new SyntaxException("Undefined function: "+functionName);
    }
    
}


