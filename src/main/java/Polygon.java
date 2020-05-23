import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.InputStream;

public class Polygon extends StackPane {
    private boolean touched = false;  // был ли клик по клетке
    private boolean mine; // есть ли в клетке мина
    private int numberB; // кол-во мин вокруг
    private double a; // длина стороны полигона
    private double x1; // позиция начала рисования
    private double y1;
    private javafx.scene.shape.Polygon poly = new javafx.scene.shape.Polygon(); // сама клетка
    private Text text = new Text(); // надпись на клетке
    Color[] numC = new Color[]{Color.BLUE, Color.GREEN, Color.DARKRED, Color.DARKBLUE, Color.RED, Color.LIGHTBLUE };
    javafx.scene.image.Image mineImage;
    javafx.scene.image.Image flagImage;
    ImageView mineIm;
    private ImageView flagIm;

    Polygon (Double a, Double x1, Double y1, boolean imagesIn){
        this.a = a;
        this.x1 = x1;
        this.y1 = y1;
        poly.setFill(Color.GRAY);
        text.setFont(Font.font(25));
        text.setVisible(false);
        poly.getPoints().addAll(getValues());
        poly.setStroke(Color.BLACK);
        poly.setStrokeWidth(1.0);
        if(imagesIn) setImages();
        if (imagesIn){
            mineIm = new ImageView(mineImage);
            flagIm = new ImageView(flagImage);
            flagIm.setVisible(false);
            mineIm.setVisible(false);
            getChildren().addAll(poly, mineIm,flagIm, text);
        }else {
            getChildren().addAll(poly, text);
        }
        setTranslateX(x1);
        setTranslateY(y1);
        setOnMouseClicked(this::open);

    }

    public void open(javafx.scene.input.MouseEvent e){
        if (e == null || e.getButton() == MouseButton.PRIMARY ) {
            if (!touched) ViewController.setOpens(1);
            touched = true;
            text.setVisible(true);
            if (mine) {
                // взрыв мины
                ViewController.setLose(true);
            } else {
                if (numberB > 0) { // кол-во мин вокруг, если нет, то зеленая клетка
                    text.setText(String.valueOf(numberB));
                    poly.setFill(Color.LIGHTGRAY);
                    text.setFill(numC[numberB-1]);
                } else {
                    text.setText("");
                    poly.setFill(Color.WHITE);
                }
            }
        }
        if (e != null && e.getButton() == MouseButton.SECONDARY && !touched) {
            if(flagIm.visibleProperty().getValue()){
                flagIm.setVisible(false);
                if (mine){
                    ViewController.setDefC(-1);
                }
            }
            else {
                 // флаг на мине
                flagIm.setVisible(true);
                if (mine){
                    ViewController.setDefC(1);
                }
            }
        }
    }

    public boolean getTouched(){
        return touched;
    }

    public void plantBomb() {
        mine = true;
    }

    public Boolean getMine() {
        return mine;
    }

    public int getNumberB(){
        return numberB;
    }

    public void setNumberB(int num){
        numberB = num;
    }
    // создает один полигон
    public Double[] getValues(){
        double y2 = y1 + a;
        double x3 = x1 + ( a / 2 * Math.sqrt(3));
        double y3 = y1 + a * 1.5;
        double x4 = x1 + (a * Math.sqrt(3));
        double y6 = y1 - a * 0.5;
        return new Double[] {x1,y1, x1,y2, x3,y3, x4, y2, x4,y1, x3, y6};
    }

    public void setImages(){
        mineImage = new javafx.scene.image.Image(this.getClass()
                .getResourceAsStream("mine.png"), 20,20,false,true);
        flagImage = new javafx.scene.image.Image(this.getClass()
                .getResourceAsStream("flag.png"),20,20,false,true);
    }
}
