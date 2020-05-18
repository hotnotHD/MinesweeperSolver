import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;

public class ViewController {
    @FXML
    public Slider sliderM;
    public Label infoMS;
    public Button newB;
    public ComboBox<Integer> chH;
    public ComboBox<Integer> chW;

    private static boolean lose = false; // условие поражения
    private static int opens; // количество открытых клеток
    private static int defC; // количество обезвреженных бомб

    @FXML
    public void initialize(){
        ObservableList<Integer> availableChoices = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9,
                10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        chH.setItems(availableChoices);
        chW.setItems(availableChoices);
        chW.setValue(5);
        chH.setValue(5);
    }

    public void change() {
        int cc = (int) sliderM.getValue();
        infoMS.setText("Mines: " + cc);
    }

    public void newGame() {
        int height = chH.getValue();
        int width = chW.getValue();
        int mineI;
        // Кол-во мин и close main menu
        Stage stage1 =(Stage) newB.getScene().getWindow();
        Label minesL = (Label) newB.getParent().getChildrenUnmodifiable().get(0);
        String mineS = minesL.getText().substring(7);
        mineI = Integer.parseInt(mineS);
        stage1.close();
        // open new game
        Stage stage = new Stage();
        if (mineI > height * width - 1){
            mineI = height * width - 1;
        }
        // сделать, чтоб размер был зависим от размера поля
        Generator gener = new Generator(mineI, height, width);
        Parent gen = gener.generate();
        Scene scene = new Scene(gen, (width + 2) * 20 * Math.sqrt(3), (height + 2) * 30);
        stage.setScene(scene);
        InputStream iconStream = Minesweeper.class.getResourceAsStream("icon.png");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        stage.show();
        final boolean[] cl = {true};
        int finalMineI = mineI;
        gener.root.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                String f = e.getTarget().getClass().getName();
                if (cl[0] && f.equals("javafx.scene.shape.Polygon")) {
                    gener.planting();
                    cl[0] = false;
                }
                if (lose){
                    try {
                        Minesweeper.openWindow(stage, "loseScreen.fxml");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    lose = false;
                    opens = 0;
                    defC = 0;
                }
            }
            if (finalMineI == defC && opens == height * width - finalMineI){
                try {
                    Minesweeper.openWindow(stage, "winScreen.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                opens = 0;
                defC = 0;
            }
        });

    }

    public static void setDefC(int c){
        defC += c;
    }

    public static void setOpens(int c){
        opens += c;
    }

    public static void setLose(Boolean b){
        lose = b;
    }
}
