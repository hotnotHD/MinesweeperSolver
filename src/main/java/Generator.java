import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import java.util.Random;

public class Generator {
    int mines;
    int w = 460;
    int h = 250;
    Polygon[][] info = new Polygon[7][11];

     Generator(int mines) {
         this.mines = mines;
    }

    public Parent generate(){
        Pane root = new Pane();
        root.setPrefSize(w,h);
        int cellsCount = 0;
        int count = 0;
        int ii = 0;
        double a = 20.0;
        double x = 20.0;
        double y = 20;

        for(int j = 0; j < 7; j++) {

            for (int i = 0; i < 11; i++) {
                Polygon pl = new Polygon(a, x, y);
                x += a * Math.sqrt(3.1);
                root.getChildren().add(pl);
                cellsCount++;
                info[j][i] = pl;
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
        numberB();
        System.out.println(mines);
        return root;
    }

    public void numberB(){
         int number = 0;
         int[][][] dev = {
                 {
                     {-1,-1},
                     {-1,0},
                     {0,1},
                     {1,0},
                     {1,-1},
                     {0,-1}
                },
                 {
                     {-1,0},
                     {-1,1},
                     {0,1},
                     {1,1},
                     {1,0},
                     {0,-1}
                 }
         };
         for ( int y = 0; y < 7; y++){
             for (int x = 0; x < 11; x++){
                 Polygon cur = info[y][x];
                 for(int i = 0 ; i < 6; i++ ) {
                     if(dev[y % 2][i][0] + y >= 0 && dev[y % 2][i][1] + x >= 0 &&
                             dev[y % 2][i][0] + y < 7 && dev[y % 2][i][1] + x < 11) {
                         if(info[y + dev[y % 2][i][0]][x + dev[y % 2][i][1]].getMine()){
                             number++;
                         }
                     }
                 }
                 cur.setNumberB(number);
                 number = 0;
             }
         }
    }
}