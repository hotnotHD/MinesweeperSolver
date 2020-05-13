import com.sun.javafx.geom.Line2D;
import com.sun.prism.Graphics;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.crypto.Mac;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class ViewController extends Application {
    @Override
    public void start(Stage stage) {

        Pane root = new Pane();
        root.setPrefSize(440, 250);

        double a = 20.0;
        double x = 20.0;
        double y = 20;
        Scene scene = new Scene(root, 440, 250);
        scene.setRoot(new Generator().generate());

        stage.setScene(scene);
        stage.setTitle("Minesweeper");
        stage.show();

    }

     public static void main(String[] args) {
         launch(args);
     }
 }