/**
 * Base class for function objects
 */
public abstract class Function 
  extends Unary
{
  public Function(Sexpr operand) {
    super(operand);
  }
  
  public String toString() {
    return getName() + "(" + operand + ")";
  }
  
}