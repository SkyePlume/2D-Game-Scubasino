package constant;

/**
 * Created by gerard on 04/05/2016.
 */
public enum GameLength {
    SHORT(50),
    LONG(100);

    private int numVal;

    GameLength(int numVal){
        this.numVal = numVal;
    }
    public int getVal() { return numVal; }
}
