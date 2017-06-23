import java.util.Map;

public class Exp
    extends Function
{
    
    public Exp(Sexpr operand) {
        super(operand);
    }
    
    public String getName() {
        return "exp";
    }
    
    public Sexpr eval(Map<String,Sexpr> map) {
        return Symbolic.exp(operand.eval(map));
    }
    
    public Sexpr diff(Sexpr x) {
        return Symbolic.mul(operand.diff(x),
                            new Exp(operand));
    }
    
}