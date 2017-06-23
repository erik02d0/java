import java.util.Map;

public class Log
    extends Function
{
    
    public Log(Sexpr operand) {
        super(operand);
    }
    
    public String getName() {
        return "log";
    }
    
    public Sexpr eval(Map<String,Sexpr> map) {
        return Symbolic.log(operand.eval(map));
    }
    
    public Sexpr diff(Sexpr x) {
        return Symbolic.div(new Constant(1), operand);
    }
    
}