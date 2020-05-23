import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.Random;

public class Generator {
    int mines; // кол-во мин
    int width; // широта окна
    int height; // высота окна
    Polygon[][] info; // массив ссылок на клетки
    Pane root = new Pane(); // само окно
    int cellsCount = 0; // счетчик

    Generator(int mines, int height, int width) {
        this.width = width;
        this.height = height;
        this.mines = mines;
        info = new Polygon[this.height][this.width];
    }
    // генерирует поле
    public Parent generate(){
        Polygon pl;
        double a = 25.0;
        double x = 20.0; // начальная позиция
        double y = 40.0;

        for(int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                pl = new Polygon(a, x, y);
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
        Text text = new Text();
        text.setText("Mines:" + mines);
        text.setFont(Font.font(20));
        text.setX(100.0);
        text.setY(20.0);
        root.getChildren().add(text);
        return root;
    }
    // проставляет бомбы
    public void planting(){
        int i = 0;
        Random r = new Random();
        while (i != mines && i < cellsCount) {
            Polygon poly = (Polygon) root.getChildren().get(r.nextInt(cellsCount));
            if(!poly.getMine() && !poly.getTouched()) {
                poly.plantBomb();
                i++;
            }
        }
        numberB();
    }
    // присваивает клеткам количество бомб вокруг
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
         for ( int y = 0; y < height; y++){
             for (int x = 0; x < width; x++){
                 Polygon cur = info[y][x];

                 for(int i = 0 ; i < 6; i++ ) {
                     if(dev[y % 2][i][0] + y >= 0 && dev[y % 2][i][1] + x >= 0 &&
                             dev[y % 2][i][0] + y < height && dev[y % 2][i][1] + x < width) {
                         if(info[y + dev[y % 2][i][0]][x + dev[y % 2][i][1]].getMine()){
                             number++;
                         }
                     }
                 }
                 cur.setNumberB(number);
                 if (cur.getTouched()){
                     cur.open(null); // открывает ту клетку, куда был первый клик
                 }
                 number = 0;
             }
         }
    }
}
