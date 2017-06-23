/**
 * Methods for symbolic arithmetic.
 */
public class Symbolic {
 
  /**
   * Perform a symbolic/numeric addition
   * Note: The method should be elaborated to handle
   * handle several special cases (e.g addititon of zero)
   */
  public static Sexpr add(Sexpr left, Sexpr right) {
    if (left.isConstant() && right.isConstant())
      return new Constant(left.getValue()+ right.getValue());
    else if (left.isConstant(0))
        return right;
    else if (right.isConstant(0))
        return left;
    else if (left.toString().equals(right.toString()))
        return Symbolic.mul(new Constant(2), left);
    else
      return new Addition(left, right);
  }
  
  /**
   * Perform a symbolic/numeric subtraction
   */
  public static Sexpr sub(Sexpr left, Sexpr right) {
      if (left.isConstant() && right.isConstant())
          return new Constant(left.getValue()-right.getValue());
      else if (right.isConstant(0))
          return left;
      else if (left.toString().equals(right.toString()))
          return new Constant(0);
      else
          return new Subtraction(left, right);
  }
  
  /**
   * Perform a symbolic/numeric multiplication
   * Note: The method should be elaborated to handle several
   * special cases (e.g multiplication with zero or one)
   */
  public static Sexpr mul(Sexpr left, Sexpr right) {
    if (left.isConstant() && right.isConstant())
      return new Constant(left.getValue() * right.getValue());
    else if (left.isConstant(0) || right.isConstant(0))
        return new Constant(0);
    else if (left.isConstant(1))
        return right;
    else if (right.isConstant(1))
        return left;
    else
      return new Multiplication(left, right);
  }
  
  public static Sexpr div(Sexpr left, Sexpr right) {
      if (left.isConstant() && right.isConstant())
          return new Constant(left.getValue()/right.getValue());
      else if (left.isConstant(0))
          return new Constant(0);
      else if (right.isConstant(1))
          return left;
      else if (right.isConstant() && right.isConstant(0))
          throw new EvaluationException("Can't divide by zero");
      else if (left.toString().equals(right.toString()))
          return new Constant(1);
      else
          return new Division(left, right);
  }
  
  /**
   * Perform a symbolic/numeric negation
   */  
  public static Sexpr negate(Sexpr operand) {
   if (operand.isConstant())
     return new Constant(-operand.getValue());
   else
     return new Negation(operand);
  }
  
  /**
   * Apply Sine to symbolic/numeric expression
   */
  public static Sexpr sin(Sexpr operand) {
        if (operand.isConstant())
            return new Constant(Math.sin(operand.getValue()));
        else
            return new Sin(operand);
    }
  
  /**
   * Apply Cosine to symbolic/numeric expression
   */
  public static Sexpr cos(Sexpr operand) {
      if (operand.isConstant())
          return new Constant(Math.cos(operand.getValue()));
      else return new Cos(operand);
  }
  
  /**
   * 
   */
  public static Sexpr exp(Sexpr operand) {
      if (operand.isConstant())
          return new Constant(Math.exp(operand.getValue()));
      else return new Exp(operand);
  }
  
  /**
   * 
   */
  public static Sexpr log(Sexpr operand) {
      if (operand.isConstant()) {
          if (operand.isConstant(0))
              throw new EvaluationException("Log not defined at zero");
          else return new Constant(Math.log(operand.getValue()));
      }
      else return new Log(operand);
  } 
}















