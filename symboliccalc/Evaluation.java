import java.util.Map;

public class Evaluation
    extends Operator
{
    
    public Evaluation(Sexpr operand) {
        super(operand);
    }
    
    public String getName() {
        return "&";
    }
    
    public Sexpr eval(Map<String, Sexpr> map) {
        return operand.eval(map).eval(map);
    }
    
    
}