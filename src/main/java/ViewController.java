import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ViewController {
    @FXML
    public Button easy;
    @FXML
    public Button normal;
    @FXML
    public Button hard;
    @FXML
    private Slider sliderM;
    @FXML
    private Label infoMS;
    @FXML
    private Button newB;
    @FXML
    private ComboBox<Integer> chH;
    @FXML
    private ComboBox<Integer> chW;

    private int cc;

    @FXML
    public void initialize(){
        ObservableList<Integer> availableChoices = FXCollections.observableArrayList( 2, 3, 4, 5, 6, 7, 8, 9,
                10, 11, 12, 13, 14, 15, 16, 17, 18, 30);
        chH.setItems(availableChoices);
        chW.setItems(availableChoices);
        chW.setValue(7);
        chH.setValue(7);
    }

    public void change() {
        int cc = (int) sliderM.getValue();
        infoMS.setText("Mines: " + cc);
    }

    public void newGame() {
        // Кол-во мин и close main menu
        Stage stage1 =(Stage) newB.getScene().getWindow();
        String mineS = infoMS.getText().substring(7);
        stage1.close();
        // open new game
        Minesweeper.fireStarter(chH.getValue(), chW.getValue(), Integer.parseInt(mineS));

    }

    public void setEasy(){
        chH.setValue(9);
        chW.setValue(9);
        sliderM.setValue(10);
        int cc = (int) sliderM.getValue();
        infoMS.setText("Mines: " + cc);
    }

    public void setNormal() {
        chH.setValue(16);
        chW.setValue(16);
        sliderM.setValue(40);
        int cc = (int) sliderM.getValue();
        infoMS.setText("Mines: " + cc);
    }

    public void setHard() {
        chH.setValue(16);
        chW.setValue(30);
        sliderM.setValue(99);
        int cc = (int) sliderM.getValue();
        infoMS.setText("Mines: " + cc);
    }
}