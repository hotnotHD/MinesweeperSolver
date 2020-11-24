import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class Generator {
    private int mines; // кол-во мин
    private int width; // широта окна
    private int height; // высота окна
    private boolean imageSet; // отключение изображений для работы тестов
    private Hexagon[][] info; // массив ссылок на клетки
    private Pane root = new Pane(); // само окно
    private static Flags flag;
    private int[][] dev = {
                    {-1,-1},
                    {-1,0},
                    {-1,1},
                    {0,1},
                    {1,1},
                    {1,0},
                    {1,-1},
                    {0,-1}
    };
    int heWi;
    static Solver solv;
    boolean solver = false;
    static int gameMode;

    Generator(int mines, int height, int width, boolean imageSet, int gameMode) {
        this.width = width;
        this.height = height;
        this.mines = mines;
        Generator.gameMode = gameMode;
        info = new Hexagon[this.height][this.width];
        this.imageSet = imageSet;
        heWi = height * width;
        if(imageSet) {flag = new Flags(new Stage());}
    }
    // генерирует поле
    public Parent generate(){
        Text text = new Text();
        Drawing dr = new Drawing(width, height, imageSet, root, info);
        dr.drow();
        if (mines > heWi - 1) mines = heWi - 1;
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
        while (i != mines && i < heWi) {
            Hexagon poly = (Hexagon) root.getChildren().get(r.nextInt(heWi));
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
         for ( int y = 0; y < height; y++){
             for (int x = 0; x < width; x++){
                 Hexagon cur = info[y][x];
                 for(int i = 0 ; i < 8; i++ ) {
                     if(dev[i][0] + y >= 0 && dev[i][1] + x >= 0 &&
                             dev[i][0] + y < height && dev[i][1] + x < width) {
                         if(info[y + dev[i][0]][x + dev[i][1]].getMine()){
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

    public int getMines(){
        return mines;
    }

    public static Flags getFlag(){
        return flag;
    }

    public Pane getRoot(){
        return root;
    }

    public Hexagon[][] getInfo(){
        return info;
    }

    public void setSolv(Solver s){
        solv = s;
        solver = true;
    }
    public static void open(int x, int y){
        if (gameMode == 2) {
            solv.open(x, y);
        }
    }
    public static void start(){
        if (gameMode == 1) solv.start();
    }
}