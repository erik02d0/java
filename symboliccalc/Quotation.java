/**
 * Represents the quotation operation
 */
import java.util.Map;

public class Quotation 
    extends Operator
{
    public Quotation(Sexpr operand) {
        super(operand);
    }
    
    public String getName() {
        return "\"";
    }
    
    public Sexpr eval(Map<String, Sexpr> map) {
        return operand;
    }
}