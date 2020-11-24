import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ToMainMenuController {
    @FXML
    private Button backTMmenu2;
    @FXML
    private Button backTMmenu;

    public void main() throws IOException {
        Generator.getFlag().getStage().close();
        Button g = backTMmenu != null ? backTMmenu : backTMmenu2;
        Stage stage1 =(Stage) g.getScene().getWindow();
        Minesweeper.statisticWrite();
        Minesweeper.openWindow(stage1, "mainMenu.fxml");
    }
}