import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Minesweeper extends Application {

    static private Generator gener;
    static private boolean firstCl = true;
    static private int finalMineI = 0;
    static private int difficult = 0;
    static private int game;

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
        firstCl = true;
    }

    public static void fireStarter(int height, int width, int mineI, int gameMode){
        game = gameMode;
        gener = new Generator(mineI, height, width, true, gameMode);
        Parent gen = gener.generate();
        Scene scene = new Scene(gen, (width + 2) * 25 , (height + 2) * 30);
        Generator.getFlag().getStage().setScene(scene);
        InputStream iconStream = Minesweeper.class.getResourceAsStream("mine.png");
        Image image = new Image(iconStream);
        Generator.getFlag().getStage().getIcons().add(image);
        Generator.getFlag().getStage().setTitle("Minesweeper");
        Generator.getFlag().getStage().show();
        mineI = gener.getMines();
        finalMineI = mineI;
        if(gameMode != 0) {
            Solver gg = new Solver(height, width, mineI);
            gener.setSolv(gg);
            if (gameMode == 1) gg.start();

        }
        gener.getRoot().setOnMouseClicked(e -> checkForEnd(e, height, width));

    }

    public static void checkForEnd(MouseEvent e, int height, int width){

        if ( e != null && e.getButton() == MouseButton.PRIMARY) {
            String f = e.getTarget().getClass().getName();
            if (f.equals("javafx.scene.shape.Polygon") && firstCl) {
                gener.planting();
                firstCl = false;
            }
            if (game == 2) Generator.solv.cheats();
        }
        if (Generator.getFlag().getLose()){
            try {
                // openAll(gener);
                Minesweeper.openWindow(null, "loseScreen.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (finalMineI == Generator.getFlag().getDefC() && Generator.getFlag().getOpens() == height * width - finalMineI){
            try {
                Minesweeper.openWindow(null, "winScreen.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static int openCur(int h, int w){
        Hexagon[][] info = gener.getInfo();
        info[w][h].open(null);
        if(firstCl) {
            gener.planting();
            firstCl = false;
        }

        if(info[w][h].getMine()) return 11;
        else return info[w][h].getNumberB();
    }



    public static void flagCur(int h, int w){
        if(game == 1) {
            Hexagon[][] info = gener.getInfo();
            info[w][h].setFlag();
        }
    }

    public static void chanceCur(int h, int w, double num){
        Hexagon[][] info = gener.getInfo();
        info[w][h].setChance(num);
    }

    public static void chanceOff(int h, int w){
        Hexagon[][] info = gener.getInfo();
        info[w][h].chanceOff();
    }

    public static void openAll(Generator gen){
        Hexagon[][] info = gen.getInfo();
        for (Hexagon[] hexagons : info) {
            for (Hexagon hexagon : hexagons) {
                hexagon.open(null);
            }
        }
    }

    public static void setDifficult(int value){
        difficult = value;
    }

    public static void statisticWrite() {
        try {
            List<String> list = Files.readAllLines(Paths.get("./src/main/resources/statistics.txt"));
            String line = list.get(difficult);
            String[] part = line.split(" ");
            int wins = Integer.parseInt(part[2]);
            int loses = Integer.parseInt(part[4]);
            if (Generator.getFlag().getLose()) loses++;
            else wins++;
            int total = loses + wins;
            int wr = wins * 100 / total;
            part[2] = "" + wins;
            part[4] = "" + loses;
            part[6] = "" + total;
            part[8] = "" + wr;
            line = String.join(" ", part);
            list.set(difficult, line);
            Files.write(Paths.get("./src/main/resources/statistics.txt"), list);
        }catch (java.io.IOException ignored) {

        }
    }
}
