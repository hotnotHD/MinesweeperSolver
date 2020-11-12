import java.lang.annotation.Native;

public class Solver {

    private int h;
    private int w;
    private Integer[][] ff;

    Solver(int h, int w, int mines) {
        this.ff = new Integer[w][h];
        this.h = h;
        this.w = w;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                ff[i][j] = 9;
            }
        }
    }

    public void start() {

        int v = Minesweeper.openCur(1, 1);
        ff [1][1] = v;
        /*
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int v = Minesweeper.openCur(i, j);
                if (v == 9) System.out.println("OOPs");
                else ff[i][j][0] = v;
                System.out.println(ff[i][j][0]);
            }
        }
        */
    }
}
