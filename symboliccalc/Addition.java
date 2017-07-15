/**
 * Represents an addition operation
 */
import java.util.Map;

public class Addition
    extends Binary
{
    public Addition(Sexpr left, Sexpr right) {
        super(left, right);
    }
    
    public String getName() {
        return "+";
    }
    
    public int priority() {
        return 20;
    }
    
    public Sexpr eval(Map<String,Sexpr> map) {
        return Symbolic.add(left.eval(map), right.eval(map)); 
    }
    
    public Sexpr diff(Sexpr x) {
        return Symbolic.add(left.diff(x), right.diff(x));
    }
}