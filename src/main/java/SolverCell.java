import java.util.ArrayList;
import java.util.List;

public class SolverCell {

    private int value = 9;
    private int closedCells = 0;
    private int flagsAround = 0;
    private int x;
    private int y;
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

    public void dropFlagsAround() {
        this.flagsAround--;
    }

    public void setClosedLow(){
        closedCells--;
    }

    public void addChance(double num){
        chances.add(num);
    }

    public List<Double> getChances(){
        return chances;
    }

    public void listClear(){
        chances.clear();
    }
}
