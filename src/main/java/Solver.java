import java.util.List;
import java.util.Random;

public class Solver {
    private int h;
    private int w;
    private SolverCell[][] field;
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
    private boolean corr = false;

    Solver(int h, int w) {
        this.field = new SolverCell[w][h];
        this.h = h;
        this.w = w;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                field[i][j] = new SolverCell(i,j);// 9 - не открытые клетки
                if ((i == 0 && (j == 0 || j == h - 1 )) ||
                        (i == w - 1 && (j == 0 || j == h - 1 )) ) field[i][j].setClosedCells(3);
                if (((i == 0 || i== w - 1) && (j > 0 && j < h - 1)) ||
                        ((j == 0 || j == h - 1) && (i > 0 && i < w - 1))) field[i][j].setClosedCells(5);
                if (field[i][j].getClosedCells() == 0) field[i][j].setClosedCells(8);
            }
        }
        openFirst();
        if (field[1][1].getValue() != 0){
            randomOpen(field[1][1]);
        }
    }

    public void start() {
        changing = true;
        while (changing) {
            changing = false;
            analyzer();
        }
        exodia();
        correction();
        correction();
        correctionCl();
    }

    public void openFirst(){
        int v = Minesweeper.openCur(1, 1);
        field[1][1].setValue(v); // открываем первую клетку и ставим ее значение.
        sayForAll(field[1][1]);
        field[1][1].setOpened();
    }

    public void analyzer (){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (field[i][j].getValue() == 0) {
                    zero(field[i][j]);
                }
                if (field[i][j].getValue() == field[i][j].getClosedCells() && field[i][j].getValue() != 0){
                    flaging(field[i][j]);
                }
                if(field[i][j].getValue() == field[i][j].getFlagsAround() && field[i][j].getValue() != 0){
                    zero(field[i][j]);
                }
            }
        }
    }

    public void open( int x, int y){
        if (!field[x][y].getOpened()) {
            int valueC = Minesweeper.openCur(x, y);
            field[x][y].setValue(valueC);
            sayForAll(field[x][y]);
            field[x][y].setOpened();
        }
    }

    public void randomOpen(SolverCell current){
        Random r = new Random();
        int randomInt = r.nextInt(8);
        int x = dev[randomInt][0] + current.getX();
        int y = dev[randomInt][1] + current.getY();
        int valueC = Minesweeper.openCur(x, y);
        field[x][y].setValue(valueC);
        sayForAll(field[x][y]);
        field[x][y].setOpened();

    }

    public void sayForAll(SolverCell current){
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if(x >= 0 && y >= 0 && x < w && y < h) {
                field[x][y].setClosedLow();
            }
        }
    }

    public void sayForFlag(SolverCell current){
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if(dev[i][0] + x >= 0 && y >= 0 && x < w && y < h) {
                field[x][y].setFlagsAround();
            }
        }
    }

    public void zero(SolverCell current){  // вынести в одну переменную коорды//
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if((x >= 0 && y >= 0 && x < w && y < h) && field[x][y].getNoTouch()) {
                int valueC = Minesweeper.openCur(x, y);
                field[x][y].setValue(valueC);
                sayForAll(field[x][y]);
                field[x][y].setOpened();
                changing = true;
            }
        }
    }

    public void flaging(SolverCell current){
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if((x >= 0 && y >= 0 && x < w && y < h) && field[x][y].getNoTouch()) {
                field[x][y].setFlaged();
                Minesweeper.flagCur(x, y);
                sayForFlag(field[x][y]);
                changing = true;
            }
        }
    }

    public void exodia(){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (corr){
                    Minesweeper.chanceOff(i,j);
                }
                int value = field[i][j].getValue();
                int flags = field[i][j].getFlagsAround();
                if(field[i][j].getOpened() && value != flags) {
                    Double num = (double) (value - flags) / (field[i][j].getClosedCells() - flags);
                    chanceForAll(field[i][j], num);
                }
            }
        }
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                List<Double> list = field[i][j].getChances();
                if(!list.isEmpty() && list.size() > 1) {
                    double chanceCorr = 1.0;
                    for (Double num : list){
                        chanceCorr *= (1 - num);
                    }
                    chanceCorr = 1 - chanceCorr;
                    field[i][j].listClear();
                    field[i][j].addChance(chanceCorr);
                }
            }
        }
    }

    public void correction(){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int valueF = field[i][j].getValue();
                int flagsF = field[i][j].getFlagsAround();
                if(field[i][j].getOpened() && valueF != flagsF) {
                    double value = (valueF - flagsF) / chancesAround(field[i][j]);
                    chancesMulti(field[i][j], value);
                }
            }
        }
        corr = true;
    }

    public void correctionCl(){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if(!field[i][j].getChances().isEmpty()){
                    field[i][j].listClear();
                }
            }
        }
    }

    public void chancesMulti(SolverCell current, double numC){
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if(x >= 0 && y >= 0 && x < w && y < h && field[x][y].getNoTouch() && !field[x][y].getChances().isEmpty()) {
                double num = field[x][y].getChances().get(0) * numC;
                field[x][y].listClear();
                field[x][y].addChance(num);
                Minesweeper.chanceCur(x, y, num);
            }
        }
    }

    public Double chancesAround(SolverCell current){
        double num = 0.0;
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if(x >= 0 && y >= 0 && x < w && y < h && field[x][y].getNoTouch() && !field[x][y].getChances().isEmpty()) {
               num += field[x][y].getChances().get(0);
            }
        }
        return num;
    }

    public void chanceForAll(SolverCell current, Double num){
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if(x >= 0 && y >= 0 && x < w && y < h && field[x][y].getNoTouch()) {
                field[x][y].addChance(num);
            }
        }
    }
}
