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

    static private Generator gener;
    static private boolean firstCl = true;

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
        gener = new Generator(mineI, height, width, true);
        Parent gen = gener.generate();
        Scene scene = new Scene(gen, (width + 2) * 25 * Math.sqrt(3), (height + 2) * 37);
        Generator.getFlag().getStage().setScene(scene);
        InputStream iconStream = Minesweeper.class.getResourceAsStream("mine.png");
        Image image = new Image(iconStream);
        Generator.getFlag().getStage().getIcons().add(image);
        Generator.getFlag().getStage().setTitle("Minesweeper");
        Generator.getFlag().getStage().show();
        mineI = gener.getMines();
        int finalMineI = mineI;
        gener.getRoot().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                String f = e.getTarget().getClass().getName();
                if (f.equals("javafx.scene.shape.Polygon") && firstCl) {
                    gener.planting();
                    firstCl = false;
                }
                if (Generator.getFlag().getLose()){
                    try {
                        openAll(gener);
                        Minesweeper.openWindow(null, "loseScreen.fxml");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
            if (finalMineI == Generator.getFlag().getDefC() && Generator.getFlag().getOpens() == height * width - finalMineI){
                try {
                    Minesweeper.openWindow(null, "winScreen.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public static int openCur(int h, int w){
        Hexagon[][] info = gener.getInfo(); // оптимизировать
        info[h][w].open(null);
        if(firstCl) {
            gener.planting();
            firstCl = false;
        }
        if(info[h][w].getMine()) return 10;
        else return info[h][w].getNumberB();
    }

    public static void openAll(Generator gen){
        Hexagon[][] info = gen.getInfo();
        for (Hexagon[] hexagons : info) {
            for (Hexagon hexagon : hexagons) {
                hexagon.open(null);
            }
        }
    }

}
