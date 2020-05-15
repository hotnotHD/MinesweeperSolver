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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;

public class Minesweeper extends Application {

    public static int opens;
    public static int defC;
    public int mineI;
    public static boolean lose = false;

    @FXML
    public Label infoSM;
    @FXML
    public Label infoMS;
    @FXML
    public Button backB;
    @FXML
    public Button newB;
    @FXML
    public Button backTMscreen;
    @FXML
    public Button backTMscreen2;
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
        Label minesL = (Label) newB.getParent().getChildrenUnmodifiable().get(1);
        String mineS = minesL.getText().substring(6);
        mineI = Integer.parseInt(mineS);
        stage1.close();
        // open new game
        Stage stage = new Stage();

        // сделать, чтоб размер был зависим от размера поля
        Generator gener = new Generator(mineI);
        Parent gen = gener.generate();
        Scene scene = new Scene(gen, 460, 300);
        stage.setScene(scene);
        stage.show();
        final boolean[] cl = {true};
        gener.root.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                String f = e.getTarget().getClass().getName();
                if (cl[0] && f.equals("javafx.scene.shape.Polygon")) {
                    gener.planting();
                    cl[0] = false;
                }
                if (lose){
                    stage.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loseScreen.fxml"));
                    Parent root1 = null;
                    try {
                        root1 = fxmlLoader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Stage stageW = new Stage();
                    stageW.setScene(new Scene(root1));
                    stageW.setTitle("Minesweeper");
                    stageW.show();
                    lose = false;
                    opens = 0;
                    defC = 0;
                }
            }
            if (e.getButton() == MouseButton.SECONDARY && mineI == defC){
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("winScreen.fxml"));
                Parent root1 = null;
                try {
                    root1 = fxmlLoader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Stage stageW = new Stage();
                stageW.setScene(new Scene(root1));
                stageW.setTitle("Minesweeper");
                stageW.show();
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

    public void main(ActionEvent actionEvent) throws IOException {
        Button g = backTMscreen != null ? backTMscreen : backTMscreen2;
        Stage stage1 =(Stage) g.getScene().getWindow();
        stage1.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("Minesweeper");
        stage.show();
    }
    // попытаться сделать функцию запуска окна отдельно
    // сделать размеры поля
}
