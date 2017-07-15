/**
 * Base class for unary operators
 */
public abstract class Operator 
    extends Unary
{
    public Operator(Sexpr operand) {
        super(operand);
    }
    
    public String toString() {
        if ( priority() > operand.priority())
            return getName() + "(" + operand + ")";
        else
            return getName() + operand;
    }
    
    public int priority() {
        return 40;
    }
}