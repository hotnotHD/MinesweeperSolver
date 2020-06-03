import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

public class Hexagon extends StackPane {
    private boolean touched = false;  // был ли клик по клетке
    private boolean mine; // есть ли в клетке мина
    private int numberB; // кол-во мин вокруг
    private Polygon poly; // сама клетка
    private Text text = new Text(); // надпись на клетке
    private Color[] numC = new Color[]
            {Color.BLUE, Color.GREEN, Color.DARKRED, Color.DARKBLUE, Color.RED, Color.LIGHTBLUE};
    private ImageView mineIm;
    private ImageView flagIm;
    private boolean imagesIn;

    Hexagon(Double a, Double x1, Double y1, boolean imagesIn) {
        this.imagesIn = imagesIn;
        poly = new HexCell(a, x1, y1).getPoly();
        text.setFont(Font.font(25));
        text.setVisible(false);
        if (imagesIn){
            setImages();
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
            if (!touched && imagesIn) Generator.getFlag().setOpens(1);
            touched = true;
            if (mine) {
                // взрыв мины
                Generator.getFlag().setLose(true);
                if (imagesIn)mineIm.setVisible(true);
            } else {
                if (imagesIn) flagIm.setVisible(false);
                text.setVisible(true);
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
                    Generator.getFlag().setDefC(-1);
                }
            }
            else {
                 // флаг на мине
                flagIm.setVisible(true);
                if (mine){
                    Generator.getFlag().setDefC(1);
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



    public void setImages(){
        Image mineImage = new Image(this.getClass().getResourceAsStream("mine.png"),
                20, 20, false, true);
        Image flagImage = new Image(this.getClass().getResourceAsStream("flag.png"),
                20, 20, false, true);
        mineIm = new ImageView(mineImage);
        flagIm = new ImageView(flagImage);
        flagIm.setVisible(false);
        mineIm.setVisible(false);
    }
}