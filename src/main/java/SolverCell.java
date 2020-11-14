import java.util.ArrayList;
import java.util.List;

public class SolverCell {

    private int value = 9;
    private int closedCells;
    private int flagsAround = 0;
    private int x;
    private int y;
    private boolean opened = false;
    private boolean flaged = false;
    private List<Double> chances = new ArrayList<>();

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

    public void setFlagsAround() {
        this.flagsAround++;
    }

    public void setOpened() {
        this.opened = true;
    }

    public boolean getOpened() {
        return opened;
    }

    public boolean getNoTouch(){
        return !opened && !flaged;
    }

    public void setClosedLow(){
        closedCells--;
    }

    public void addChance(double num){
        chances.add(num);
    }

    public void setFlaged() {
        this.flaged = true;
    }
    public List<Double> getChances(){
        return chances;
    }

    public void listClear(){
        chances.clear();
    }
}
