import javafx.stage.Stage;

public class Flags {
    public boolean lose; // условие поражения
    public int opens; // количество открытых клеток
    public int defC; // количество обезвреженных бомб
    public Stage stage;

    Flags(Stage stage){
       this.opens = 0;
       this.defC = 0;
       this.stage = stage;
       this.lose = false;
    }

}
