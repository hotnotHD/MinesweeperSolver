import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexCell {
    private double a;
    private double x1;
    private double y1;
    private Polygon poly = new Polygon();

    HexCell(double a, double x1, double y1){
        this.a = a;
        this.x1 = x1;
        this.y1 = y1;
        poly.setFill(Color.GRAY);
        poly.setStroke(Color.BLACK);
        poly.setStrokeWidth(1.0);
    }

    public Polygon getPoly(){
        poly.getPoints().addAll(getValues());
        return poly;
    }

    // создает один полигон
    public Double[] getValues(){
        double x2 = x1 + a;
        double y2 = y1 - a;
        return new Double[] {x1,y1, x2,y1, x2,y2, x1,y2};
    }
}
