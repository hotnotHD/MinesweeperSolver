import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Generator {

     Generator() {

    }

    public Parent generate(){
        Pane root = new Pane();
        root.setPrefSize(440,250);

        double a = 20.0;
        double x = 20.0;
        double y = 20;

        for(double j = 0; j<(250.0)/40.0;j++) {
            for (double i = 0; i < 380; i += a * Math.sqrt(3)) {
                Polygon pl = new Polygon(a, x, y);
                x += a * Math.sqrt(3.1);
                root.getChildren().add(pl);
            }
            y += a * 1.5;
            x = 20.0;
            if (j % 2 == 0) {
                x += a * Math.sqrt(3) / 2;
            }
        }
        return root;
    }
}
