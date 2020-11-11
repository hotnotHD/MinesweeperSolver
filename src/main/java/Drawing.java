import javafx.scene.layout.Pane;

public class Drawing {
    private int width; // широта окна
    private int height; // высота окна
    private boolean imageSet;
    private Pane root;
    private Hexagon[][] info;

    Drawing(int width, int height, boolean imageSet, Pane root, Hexagon[][] info){
        this.height = height;
        this.width = width;
        this.imageSet = imageSet;
        this.root = root;
        this.info = info;
    }

    public void drow(){
        Hexagon pl;
        double a = 25.0;
        double x = 20.0; // начальная позиция
        double y = 40.0;

        for(int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                pl = new Hexagon(a, x, y, imageSet);
                x += a;
                root.getChildren().add(pl);
                info[j][i] = pl;
            }
            y += a;
            x = 20.0;
        }
    }
}
