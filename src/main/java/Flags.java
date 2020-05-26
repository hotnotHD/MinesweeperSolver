import javafx.stage.Stage;

public class Flags {
    private boolean lose = false; // условие поражения
    private int opens; // количество открытых клеток
    private int defC; // количество обезвреженных бомб
    private Stage stage;

    Flags( Stage stage ){
        this.stage = stage;
    }
    public boolean getLose(){
        return lose;
    }
    public  int getOpens(){
        return opens;
    }
    public int getDefC(){
        return defC;
    }
    public void setDefC(int c){
        defC += c;
    }

    public void setOpens(int c){
        opens += c;
    }

    public void setLose(Boolean b){
        lose = b;
    }

    public Stage getStage(){
        return stage;
    }

    public void newFlags(){
        lose = false; // условие поражения
        opens = 0; // количество открытых клеток
        defC = 0;
    }
}
