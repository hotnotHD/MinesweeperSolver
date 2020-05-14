import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Polygon extends StackPane {
    private boolean mine;
    private int numberB;
    private double a;
    private double x1;
    private double y1;
    private javafx.scene.shape.Polygon poly = new javafx.scene.shape.Polygon();
    private Text text = new Text();

    Polygon (Double a, Double x1, Double y1){
        this.a = a;
        this.x1 = x1;
        this.y1 = y1;
        poly.setFill(Color.GRAY);
        text.setFont(Font.font(10));
        text.setText("gg");
        text.setVisible(false);
        poly.getPoints().addAll(getValues());
        getChildren().addAll(poly,text);
        setTranslateX(x1);
        setTranslateY(y1);
        setOnMouseClicked(e -> open());
    }

    public void open(){
        text.setVisible(true);
        if (mine){
            text.setText("Boom");
        }else {
            if (numberB > 0) {
                text.setText(String.valueOf(numberB));
            } else {
                text.setText("");
                poly.setFill(Color.GREEN);
            }
        }
    }

    public void plantBomb(Boolean b) {
        mine = b;
    }

    public Boolean getMine() {
        return mine;
    }

    public void setNumberB(int num){
        numberB = num;
    }

    public Double[] getValues(){
        double y2 = y1 + a;
        double x3 = x1 + ( a / 2 * Math.sqrt(3));
        double y3 = y1 + a * 1.5;
        double x4 = x1 + (a * Math.sqrt(3));
        double y6 = y1 - a * 0.5;
        return new Double[] {x1,y1, x1,y2, x3,y3, x4, y2, x4,y1, x3, y6};
    }


}
