import java.util.Map;

public class Differentiation
    extends Binary
{
    
    public Differentiation(Sexpr left, Sexpr right) {
        super(left, right);
    }
    
    public String getName() {
        return "'";
    }
    
    public int priority() {
        return 50;
    }
    
    public Sexpr eval(Map<String,Sexpr> map) {
        return left.eval(map).diff(right);
    }
    
}