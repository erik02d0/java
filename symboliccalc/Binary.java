/**
 * Base class for all binary operators
 */
public abstract class Binary 
    extends Sexpr
{
    protected Sexpr left;
    protected Sexpr right;
    
    public Binary(Sexpr left, Sexpr right) {
        this.left = left;
        this.right = right;
    }
    
    /* 
     * The toString-method should be refined so that parentheses are used only when needed
     */
    public String toString() {
        String lef = left.toString();
        String rig = right.toString();
        if (left.priority() < priority())
            lef = "("+lef+")";
        if (right.priority() <= priority())
            rig = "("+rig+")";
        return lef+getName()+rig;
    }
}
