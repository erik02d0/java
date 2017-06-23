import java.util.Map;

public class Division
    extends Binary
{
    public Division(Sexpr left, Sexpr right) {
        super(left, right);
    }
    
    public String getName() {
        return "/";
    }
    
    public int priority() {
        return 30; // correct?
    }
    
    public Sexpr eval(Map<String, Sexpr> map) {
        return Symbolic.div(left.eval(map), right.eval(map));
    }
    
    public Sexpr diff(Sexpr x) {
        return Symbolic.div(Symbolic.sub(Symbolic.mul(left.diff(x),
                                                      right),
                                         Symbolic.mul(left,
                                                      right.diff(x))),
                            Symbolic.mul(right, right));
    }
}