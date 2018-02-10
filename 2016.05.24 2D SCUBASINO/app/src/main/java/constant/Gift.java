package constant;

/**
 * Created by js884 on 29/04/2016.
 */
public enum Gift {
    OXYGEN(40),
    WATERJET(5),
    BUBBLE(10),
    BOMBDEFUSAL(50),
    MACHETE(25);

    private int numVal;

    Gift(int numVal){
        this.numVal = numVal;
    }
    public int getVal() { return numVal; }
}


