import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;

public class Minesweeper extends Application {
    @FXML
    public Label infoSM;
    @FXML
    public Label infoMS;
    @FXML
    public Button backB;
    @FXML
    public Button newB;
    @FXML
    Button butt;
    @FXML
    Slider scrollerM;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Minesweeper 0.0001");

        InputStream iconStream = getClass().getResourceAsStream("icon.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void openSettings() throws IOException {
        // закрытие старого окна
        Stage stage1 =(Stage) butt.getScene().getWindow();
        stage1.close();
        // открываем настройки
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Minesweeper");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public void change(MouseEvent mouseEvent) throws IOException {
        int cc = (int) scrollerM.getValue();
        infoSM.setText(String.valueOf(cc));
    }

    public void back(ActionEvent actionEvent) throws IOException {
        // Закрываем окно
        Stage stage1 =(Stage) backB.getScene().getWindow();
        stage1.close();
        // Загружаем новое окно (Главное меню)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("Minesweeper");
        stage.show();
        // Лэйбл принимает значение кол-ва мин
        Label ss = (Label) root1.getChildrenUnmodifiable().get(1);
        Slider sl = (Slider) backB.getScene().getRoot().getChildrenUnmodifiable().get(0);
        ss.setText("Mines:" + (int) sl.getValue());
    }

    public void newGame(ActionEvent actionEvent) {
        // Кол-во мин и close main menu
        Stage stage1 =(Stage) newB.getScene().getWindow();
        Label mines = (Label) newB.getParent().getChildrenUnmodifiable().get(1);
        String mine = mines.getText().substring(6);
        int mineC = Integer.parseInt(mine);
        stage1.close();
        // open new game
        Stage stage = new Stage();

        // сделать, чтоб размер был зависим от размера поля

        Scene scene = new Scene(new Generator(mineC).generate(), 460, 250);
        stage.setScene(scene);
        stage.show();
    }
}