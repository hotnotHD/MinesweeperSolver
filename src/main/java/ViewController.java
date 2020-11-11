import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ViewController {
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
                10, 11, 12, 13, 14, 15, 16);
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
        // Кол-во мин и close main menu
        Stage stage1 =(Stage) newB.getScene().getWindow();
        Label minesL = (Label) newB.getParent().getChildrenUnmodifiable().get(0);
        String mineS = minesL.getText().substring(7);
        stage1.close();
        // open new game
        Minesweeper.fireStarter(chH.getValue(), chW.getValue(), Integer.parseInt(mineS));
        Solver gg = new Solver(chH.getValue(), chW.getValue(), cc);
        gg.start();
    }
}