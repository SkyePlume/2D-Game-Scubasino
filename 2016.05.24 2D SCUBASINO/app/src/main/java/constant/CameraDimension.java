package constant;

/**
 * Created by gerard on 04/05/2016.
 */
public enum CameraDimension {
    WIDTH(480),
    HEIGHT(800);

    private int numVal;

    CameraDimension(int numVal){
        this.numVal = numVal;
    }
    public int getVal() { return numVal; }
}