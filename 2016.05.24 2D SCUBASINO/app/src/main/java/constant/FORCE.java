package constant;

/**
 * Created by js884 on 29/04/2016.
 */
public enum FORCE {
    PUSHMAX(10),
    PUSH(5),
    PULL(-5),
    PULLMIN(-2),
    PULLMAX(-10);


    private int numVal;

    FORCE(int numVal){
        this.numVal = numVal;
    }
    public int getVal() { return numVal; }
}