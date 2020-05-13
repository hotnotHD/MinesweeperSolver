import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.util.Random;

public class Generator {
    int mines;

     Generator(int mines) {
         this.mines = mines;
    }

    public Parent generate(){
        Pane root = new Pane();
        root.setPrefSize(440,250);
        int cellsCount = 0;
        int count = 0;
        int ii = 0;
        double a = 20.0;
        double x = 20.0;
        double y = 20;

        for(double j = 0; j<(250.0)/40.0;j++) {
            for (double i = 0; i < 380; i += a * Math.sqrt(3)) {
                Polygon pl = new Polygon(a, x, y);
                x += a * Math.sqrt(3.1);
                root.getChildren().add(pl);
                cellsCount++;
            }
            y += a * 1.5;
            x = 20.0;
            if (j % 2 == 0) {
                x += a * Math.sqrt(3) / 2;
            }

        }
        Random r = new Random();
        while (ii != mines || ii >= cellsCount) {
            Polygon gg = (Polygon) root.getChildren().get(r.nextInt(cellsCount - 1));
            if(!gg.getMine()) {
                gg.plantBomb(true);
                ii++;
            }
            if (count == cellsCount - 1) {
                count = 0;
            }
            count++;
        }

        System.out.println(mines);
        return root;
    }
}
