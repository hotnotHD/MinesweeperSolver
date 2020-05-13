import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// tester

public class ViewController extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Generator(20).generate(), 440, 250);

        stage.setScene(scene);
        stage.setTitle("Minesweeper");
        stage.show();
    }

     public static void main(String[] args) {
         launch(args);
     }
 }