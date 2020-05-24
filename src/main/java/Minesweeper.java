import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;

public class Minesweeper extends Application {
    private static boolean lose = false; // условие поражения
    private static int opens; // количество открытых клеток
    private static int defC; // количество обезвреженных бомб
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        openWindow(primaryStage, "mainMenu.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void openWindow (Stage stage, String fxmlLoadName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Minesweeper.class.getResource(fxmlLoadName));
        Parent root1 = fxmlLoader.load();
        Stage stageW = new Stage();
        stageW.setScene(new Scene(root1));
        stageW.setTitle("Minesweeper");
        InputStream iconStream = Minesweeper.class.getResourceAsStream("mine.png");
        Image image = new Image(iconStream);
        stageW.getIcons().add(image);
        if (stage != null) stage.close();
        else {
            stageW.initModality(Modality.APPLICATION_MODAL);
        }
        stageW.show();
    }

    public static void fireStarter(int height, int width, int mineI){
        stage = new Stage();
        Generator gener = new Generator(mineI, height, width, true);
        Parent gen = gener.generate();
        Scene scene = new Scene(gen, (width + 2) * 25 * Math.sqrt(3), (height + 2) * 37);
        stage.setScene(scene);
        InputStream iconStream = Minesweeper.class.getResourceAsStream("mine.png");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        stage.setTitle("Minesweeper");
        stage.show();
        mineI = gener.getMines();
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
                        openAll(gener);
                        Minesweeper.openWindow(null, "loseScreen.fxml");
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
                    Minesweeper.openWindow(null, "winScreen.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                opens = 0;
                defC = 0;
            }
        });
    }
    public static void openAll(Generator gen){
        Hexagon[][] info = gen.info;
        for (Hexagon[] hexagons : info) {
            for (Hexagon hexagon : hexagons) {
                hexagon.open(null);
            }
        }
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

    public static Stage getStage(){
        return stage;
    }
}
