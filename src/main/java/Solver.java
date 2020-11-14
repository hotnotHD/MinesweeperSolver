import java.lang.annotation.Native;
import java.util.List;
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
    private boolean changing = true;

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
                        ((j == 0 || j == h - 1) && (i > 0 && i < w - 1))) ff[i][j].setClosedCells(5);
                if (ff[i][j].getClosedCells() == 0) ff[i][j].setClosedCells(8);
            }
        }
    }

    public void start() {

        int v = Minesweeper.openCur(1, 1);
        ff [1][1].setValue(v); // открываем первую клетку и ставим ее значение.
        sayForAll(ff[1][1]);
        ff[1][1].setOpened();

        if (ff[1][1].getValue() != 0){
            randomOpen(ff[1][1]);
        }

        while (changing) {
            changing = false;
            analyzer();
        }
        exodia();
        correction();
        correction();
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                if (!ff[i][j].getChances().isEmpty()) {
                    double nums = ff[i][j].getChances().get(0);
                    String result = String.format("%.2f", nums);
                    System.out.print(result + " ");// 9 - не открытые клетки
                } else  System.out.print("0" + "   ");
            }
            System.out.println("");
        }


    }

    public void analyzer (){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (ff[i][j].getValue() == 0) {
                    zero(ff[i][j]);
                }
                if (ff[i][j].getValue() == ff[i][j].getClosedCells() && ff[i][j].getValue() != 0){
                    flaging(ff[i][j]);
                }
                if(ff[i][j].getValue() == ff[i][j].getFlagsAround() && ff[i][j].getValue() != 0){
                    zero(ff[i][j]);
                }
            }
        }
    }

    public void randomOpen(SolverCell current){
        Random r = new Random();
        int randomInt = r.nextInt(8);
        int x = dev[randomInt][0];
        int y = dev[randomInt][1];
        int valueC = Minesweeper.openCur(x + current.getX(), y + current.getY());
        ff[x + current.getX()][y + current.getY()].setValue(valueC);
        sayForAll(ff[x + current.getX()][y + current.getY()]);
        ff[x + current.getX()][y + current.getY()].setOpened();

    }

    public void sayForAll(SolverCell current){
        for(int i = 0 ; i < 8; i++ ) {
            if(dev[i][0] + current.getX() >= 0 && dev[i][1] + current.getY() >= 0 &&
                    dev[i][0] + current.getX() < w && dev[i][1] + current.getY() < h) {
                ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].setClosedLow();
            }
        }
    }

    public void sayForFlag(SolverCell current){
        for(int i = 0 ; i < 8; i++ ) {
            if(dev[i][0] + current.getX() >= 0 && dev[i][1] + current.getY() >= 0 &&
                    dev[i][0] + current.getX() < w && dev[i][1] + current.getY() < h) {
                ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].setFlagsAround();
            }
        }
    }

    public void zero(SolverCell current){  // вынести в одну переменную коорды//
        for(int i = 0 ; i < 8; i++ ) {
            if((dev[i][0] + current.getX() >= 0 && dev[i][1] + current.getY() >= 0 &&
                    dev[i][0] + current.getX() < w && dev[i][1] + current.getY() < h) &&
                    ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].getNoTouch()) {
                int valueC = Minesweeper.openCur(dev[i][0] + current.getX(), dev[i][1] + current.getY());
                ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].setValue(valueC);
                sayForAll(ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()]);
                ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].setOpened();
                changing = true;
            }
        }
    }

    public void flaging(SolverCell current){
        int counter = 0;
        for(int i = 0 ; i < 8; i++ ) {
            if((dev[i][0] + current.getX() >= 0 && dev[i][1] + current.getY() >= 0 &&
                    dev[i][0] + current.getX() < w && dev[i][1] + current.getY() < h) &&
                    ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].getNoTouch()) {
                ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].setFlaged();
                Minesweeper.flagCur(dev[i][0] + current.getX(), dev[i][1] + current.getY());
                sayForFlag(ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()]);
                changing = true;
            }
        }
    }

    public void exodia(){

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if(ff[i][j].getOpened() && ff[i][j].getValue() != ff[i][j].getFlagsAround()) {
                    Double num = (double) ff[i][j].getValue() / (ff[i][j].getClosedCells() - ff[i][j].getFlagsAround());
                    chanceForAll(ff[i][j], num);
                }
            }
        }
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if(!ff[i][j].getChances().isEmpty() && ff[i][j].getChances().size() > 1) {
                    double chanceCorr = 1.0;
                    for (Double num : ff[i][j].getChances()){
                        chanceCorr *= (1 - num);
                    }
                    chanceCorr = 1 - chanceCorr;
                    ff[i][j].listClear();
                    ff[i][j].addChance(chanceCorr);
                }
            }
        }
    }

    public void correction(){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if(ff[i][j].getOpened() && ff[i][j].getValue() != ff[i][j].getFlagsAround()) {
                    double value = ff[i][j].getValue() / chancesAround(ff[i][j]);
                    chancesMulti(ff[i][j], value);
                }
            }
        }
    }

    public void chancesMulti(SolverCell current, double numC){
        for(int i = 0 ; i < 8; i++ ) {
            if(dev[i][0] + current.getX() >= 0 && dev[i][1] + current.getY() >= 0 &&
                    dev[i][0] + current.getX() < w && dev[i][1] + current.getY() < h &&
                    ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].getNoTouch() &&
                    !ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].getChances().isEmpty()) {
                 double num = ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].getChances().get(0);
                ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].listClear();
                ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].addChance(num * numC);
            }
        }
    }

    public Double chancesAround(SolverCell current){
        double num = 0.0;
        for(int i = 0 ; i < 8; i++ ) {
            if(dev[i][0] + current.getX() >= 0 && dev[i][1] + current.getY() >= 0 &&
                    dev[i][0] + current.getX() < w && dev[i][1] + current.getY() < h &&
                    ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].getNoTouch() &&
                    !ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].getChances().isEmpty()) {
               num += ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].getChances().get(0);
            }
        }
        return num;
    }

    public void chanceForAll(SolverCell current, Double num){
        for(int i = 0 ; i < 8; i++ ) {
            if(dev[i][0] + current.getX() >= 0 && dev[i][1] + current.getY() >= 0 &&
                    dev[i][0] + current.getX() < w && dev[i][1] + current.getY() < h &&
                    ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].getNoTouch()) {
                ff[dev[i][0] + current.getX()][dev[i][1] + current.getY()].addChance(num);
            }
        }
    }
}
