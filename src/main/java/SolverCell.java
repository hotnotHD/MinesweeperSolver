public class SolverCell {

    private int value = 9;
    private int closedCells;
    private int flagsAround;
    private int x;
    private int y;
    private boolean opened = false;

    SolverCell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getClosedCells() {
        return closedCells;
    }

    public void setClosedCells(int closedCells) {
        this.closedCells = closedCells;
    }

    public int getFlagsAround() {
        return flagsAround;
    }

    public void setFlagsAround(int flagsAround) {
        this.flagsAround = flagsAround;
    }

    public void setOpened() {
        this.opened = true;
    }

    public boolean getOpened() {
        return opened;
    }

    public void setClosedLow(){
        closedCells = closedCells - 1;
    }
}
