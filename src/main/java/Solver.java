public class Solver {

    private int h;
    private int w;
    private Integer[][][] ff;

    Solver(int h, int w, int mines) {
        this.ff = new Integer[w][h][3];
        this.h = h;
        this.w = w;
    }

    public void start() {

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int v = Minesweeper.openCur(i, j);
                if (v == 9) System.out.println("OOPs");
                else ff[i][j][0] = v;
                System.out.println(ff[i][j][0]);
            }
        }
    }
}
