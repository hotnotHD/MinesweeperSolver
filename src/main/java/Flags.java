import javafx.stage.Stage;

public class Flags {
    private boolean lose; // условие поражения
    private int opens; // количество открытых клеток
    private int defC; // количество обезвреженных бомб
    private Stage stage;

    Flags(Stage stage){
       this.opens = 0;
       this.defC = 0;
       this.stage = stage;
       this.lose = false;
    }

    public boolean getLose(){
        return lose;
    }

    public void setLose(boolean i){
        lose = i;
    }

    public int getOpens(){
        return opens;
    }

    public void setOpens(int i){
        opens += i;
    }

    public int getDefC(){
        return defC;
    }

    public void setDefC(int i){
        defC += i;
    }

    public Stage getStage(){
        return stage;
    }
}
