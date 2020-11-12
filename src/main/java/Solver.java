import java.lang.annotation.Native;
import java.util.Random;

public class Solver {

    private int zeroes;
    private int h;
    private int w;
    private SolverCell[][] ff;
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

    Solver(int h, int w, int mines) {
        this.ff = new SolverCell[w][h];
        this.h = h;
        this.w = w;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                ff[i][j] = new SolverCell(i,j);// 9 - не открытые клетки
                if ((i == 0 && (j == 0 || j == h - 1 )) ||
                        (i == w - 1 && (j == 0 || j == h - 1 )) ) ff[i][j].setClosedCells(3);
                if (((i == 0 || i== w - 1) && (j > 0 && j < h - 1)) ||
                        ((j == 0 || j == w - 1) && (i > 0 && i < w - 1))) ff[i][j].setClosedCells(5);
                if (ff[i][j].getClosedCells() == 0) ff[i][j].setClosedCells(8);
            }
        }
    }

    public void start() {

        int v = Minesweeper.openCur(1, 1);
        ff [1][1].setValue(v); // открываем первую клетку и ставим ее значение.
        if (ff[1][1].getValue() == 0) {
            zeroes ++;
            zero(ff[1][1]);
        } else {
            sayForAll(ff[1][1]);
            ff[1][1].setOpened();
            Random r = new Random();
            int randomInt = r.nextInt(8);
            int valueC = Minesweeper.openCur(dev[randomInt][0] + 1, dev[randomInt][1] + 1);
            ff[dev[randomInt][0] + 1][dev[randomInt][1] + 1].setValue(valueC);

            if ( ff[dev[randomInt][0] + 1][dev[randomInt][1] + 1].getValue() == 0){
                zeroes ++;
                zero(ff[1][1]);
            }
            else {
                sayForAll(ff[dev[randomInt][0] + 1][dev[randomInt][1] + 1]);
                ff[dev[randomInt][0] + 1][dev[randomInt][1] + 1].setOpened();
            }
        }
        analyzer();
       // analyzer();


        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                System.out.print( ff[j][i].getClosedCells() + " ");// 9 - не открытые клетки
            }
            System.out.println("");
        }


    }

    public void analyzer (){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (ff[i][j].getValue() == 0) {
                    zero(ff[i][j]);
                    ff[i][j].setOpened();
                }
            }
        }
    }

    public void sayForAll(SolverCell current){
        for(int i = 0 ; i < 8; i++ ) {
            if(dev[i][0] + current.getX() >= 0 && dev[i][1] + current.getY() >= 0 &&
                    dev[i][0] + current.getX() < w && dev[i][1] + current.getY() < h) {
                ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].setClosedLow();
            }
        }
    }

    public void zero(SolverCell current){  // вынести в одну переменную коорды//
        for(int i = 0 ; i < 8; i++ ) {
            if((dev[i][0] + current.getX() >= 0 && dev[i][1] + current.getY() >= 0 &&
                    dev[i][0] + current.getX() < w && dev[i][1] + current.getY() < h) &&
                    !ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].getOpened()) {
                int valueC = Minesweeper.openCur(dev[i][0] + current.getX(), dev[i][1] + current.getY());
                ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].setValue(valueC);
                sayForAll(ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()]);
                ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].setOpened();
            }
        }
    }
}
