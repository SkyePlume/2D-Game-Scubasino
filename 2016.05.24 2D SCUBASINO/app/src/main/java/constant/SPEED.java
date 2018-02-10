package constant;

/**
 * Created by gja10 on 19/02/2016.
 */
public enum SPEED {
    SLOW(30),
    MEDIUM(20),
    FAST(10);

private int numVal;

    SPEED(int numVal){
        this.numVal = numVal;
    }
    public int getVal() { return numVal; }
}
