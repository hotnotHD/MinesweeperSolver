import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ViewController {
    @FXML
    public Button easy;
    @FXML
    public Button normal;
    @FXML
    public Button hard;
    @FXML
    public ComboBox<String> gameModes;
    @FXML
    public TextField minesCount;
    @FXML
    public Label infoMS;
    @FXML
    public Label diff0;
    @FXML
    public Label diff2;
    @FXML
    public Label diff1;
    @FXML
    public Label custom;
    @FXML
    private Button newB;
    @FXML
    private ComboBox<Integer> chH;
    @FXML
    private ComboBox<Integer> chW;

    int gameMode;
    @FXML
    public void initialize() throws IOException {
        ObservableList<Integer> availableChoices = FXCollections.observableArrayList( 2, 3, 4, 5, 6, 7, 8, 9,
                10, 11, 12, 13, 14, 15, 16, 17, 18, 30);
        chH.setItems(availableChoices);
        chW.setItems(availableChoices);
        chW.setValue(7);
        chH.setValue(7);
        ObservableList<String> availableChoicesGM = FXCollections.observableArrayList( "Fair game",
                "Solver mode", "Cheat mode");
        gameModes.setItems(availableChoicesGM);
        gameModes.setValue("Fair game");
        List<String> list = Files.readAllLines(Paths.get("./src/main/resources/statistics.txt"));
        custom.setText(list.get(0));
        diff0.setText(list.get(1));
        diff1.setText(list.get(2));
        diff2.setText(list.get(3));
    }

    public void setMinesCount(){
        if( !minesCount.getText().isEmpty() && !minesCount.getText().matches( "((100)|[0-9]{0,2})")){
            minesCount.replaceText(minesCount.getText().length() - 1 ,minesCount.getText().length(), "");
        }
    }

    public void newGame() {
        // Кол-во мин и close main menu
        int mi = 0;
        switch (gameModes.getValue()){
            case "Fair game":
                gameMode = 0;
                break;
            case "Solver mode":
                gameMode = 1;
                break;
            case "Cheat mode":
                gameMode = 2;
                break;
        }
        if (!minesCount.getText().isEmpty())mi = Integer.parseInt(minesCount.getText());
        if (chH.getValue() == 9 && chW.getValue() == 9 && mi == 10) Minesweeper.setDifficult(1);
        if (chH.getValue() == 16 && chW.getValue() == 16 && mi == 40) Minesweeper.setDifficult(2);
        if (chW.getValue() == 30 && chH.getValue() == 16 && mi == 99) Minesweeper.setDifficult(3);
        Stage stage1 =(Stage) newB.getScene().getWindow();
        stage1.close();
        // open new game
        Minesweeper.fireStarter(chH.getValue(), chW.getValue(), mi, gameMode);
    }

    public void setEasy(){
        chH.setValue(9);
        chW.setValue(9);
        minesCount.setText("10");
    }

    public void setNormal() {
        chH.setValue(16);
        chW.setValue(16);
        minesCount.setText("40");
    }

    public void setHard() {
        chH.setValue(16);
        chW.setValue(30);
        minesCount.setText("99");
    }
}