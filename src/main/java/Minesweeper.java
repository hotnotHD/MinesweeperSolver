import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;

public class Minesweeper extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        openWindow(primaryStage, "mainScene.fxml" );
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void openWindow (Stage stage, String fxmlLoadName) throws IOException {
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Minesweeper.class.getResource(fxmlLoadName));
        Parent root1 = fxmlLoader.load();
        Stage stageW = new Stage();
        stageW.setScene(new Scene(root1));
        stageW.setTitle("Minesweeper");

        InputStream iconStream = Minesweeper.class.getResourceAsStream("icon.png");
        Image image = new Image(iconStream);
        stageW.getIcons().add(image);

        stageW.show();
    }
}
