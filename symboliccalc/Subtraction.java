import java.util.Map;

public class Subtraction
    extends Binary
{
    public Subtraction(Sexpr left, Sexpr right) {
        super(left, right);
    }
    
    public String getName() {
        return "-";
    }
    
    public int priority() {
        return 20; // same as in Addition; change it?
    }
    
    public Sexpr eval(Map<String, Sexpr> map) {
        return Symbolic.sub(left.eval(map), right.eval(map));
    }
    
    public Sexpr diff(Sexpr x) {
        return Symbolic.sub(left.diff(x), right.diff(x));
    }
    
}