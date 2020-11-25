import java.util.List;

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
    SolverCell minChance;
    double curChance = 1.0;
    int mines;
    int flagsOnMap = 0;
    int openedCells;
    boolean lose = false;
    boolean win = false;
    boolean first = true;

    Solver(int h, int w, int mines) {
        this.field = new SolverCell[w][h];
        this.h = h;
        this.w = w;
        this.mines = mines;
        openedCells = h * w;
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
    }

    public void cheats(){
        analyz();
        exodia();
        correction();
        correction();
        correction2();
        correctionCl();
    }

    public void start() {
        if (first) {
            openFirst();
            first = false;
        }
        while(!lose && !win) {
            changing = true;
            while (changing) {
                changing = false;
                analyzer();
            }
            if (mines != 0) {
                curChance = 1.0;
                exodia();
                correction();
                correction();
                correctionCl();
                if (minChance != null && minChance.getValue() == 9) {
                    open(minChance.getX(), minChance.getY());
                    changing = true;
                }
                if(mines == flagsOnMap && openedCells == 0) {
                    win = true;
                }
                if (!lose && !win) {
                    if (mines - flagsOnMap == openedCells) {
                        flagAll();
                        changing = true;
                    }
                }
            }else win = true;
            if(!changing) {
                openFirstOne();
                changing = false;
            }
        }
        Minesweeper.checkForEnd(null, h, w);
    }


    public void analyz(){
        changing = true;
        while (changing) {
            changing = false;
            opener();
        }

    }
    public void openFirst(){
        int v = Minesweeper.openCur(1, 1);
        openedCells--;
        field[1][1].setValue(v);
        sayForAll(field[1][1]);
    }

    public void analyzer (){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (field[i][j].getValue() == 0) {
                    zero(field[i][j]);
                }
                if (field[i][j].getValue() == field[i][j].getClosedCells() && field[i][j].getValue() != 0){
                    flaging(i, j);
                }
                if(field[i][j].getValue() == field[i][j].getFlagsAround() && field[i][j].getValue() != 0){
                    zero(field[i][j]);
                }
            }
        }
    }

    public void opener(){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (field[i][j].getValue() == 0) {
                    zero(field[i][j]);
                }
            }
        }
    }

    public void open( int x, int y){
        if (field[x][y].getValue() == 10){
            int valueC = Minesweeper.openCur(x, y);
            unFlagOne(x, y);
            field[x][y].setValue(valueC);
        }
        if (field[x][y].getValue() == 9) {
            int valueC = Minesweeper.openCur(x, y);
            openedCells--;
            field[x][y].setValue(valueC);
            sayForAll(field[x][y]);
            if(valueC == 11) {
                lose = true;
            }
        }
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

    public void zero(SolverCell current){
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if((x >= 0 && y >= 0 && x < w && y < h) && field[x][y].getValue() == 9) {
                int valueC = Minesweeper.openCur(x, y);
                if(valueC == 11) {
                    lose = true;
                }
                openedCells--;
                field[x][y].setValue(valueC);
                sayForAll(field[x][y]);
                changing = true;
            }
        }
    }

    public void flagOne(int x, int y){
        field[x][y].setValue(10);
        openedCells--;
        flagsOnMap++;
        sayForFlag(field[x][y]);
    }

    public void flaging(int xx, int yy){
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + xx;
            int y = dev[i][1] + yy;
            if((x >= 0 && y >= 0 && x < w && y < h) && field[x][y].getValue() == 9) {
                field[x][y].setValue(10);
                Minesweeper.flagCur(x, y);
                openedCells--;
                flagsOnMap++;
                sayForFlag(field[x][y]);
                changing = true;
            }
        }
    }

    public void unFlagOne(int x, int y){
        field[x][y].setValue(9);
        openedCells++;
        flagsOnMap--;
        sayForUnFlag(field[x][y]);
    }
    public void sayForUnFlag(SolverCell current){
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if(dev[i][0] + x >= 0 && y >= 0 && x < w && y < h) {
                field[x][y].dropFlagsAround();
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

                if(field[i][j].getValue() < 9 && field[i][j].getClosedCells() != 0) {
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
                        if (num == 0) {
                            chanceCorr = 1;
                            break;
                        }
                        if (num == 1) chanceCorr = 0;
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
                if(valueF == flagsF){
                    chancesMulti(field[i][j], 0);
                }
                if(field[i][j].getValue() < 9 && valueF != flagsF) {
                    double chance = chancesAround(field[i][j]);
                    if(chance != 0) {
                        double value = (valueF - flagsF) / chance;
                        chancesMulti(field[i][j], value);
                    } else chancesMulti(field[i][j], 0);
                }
            }
        }
        corr = true;
    }
    public void correction2(){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int valueF = field[i][j].getValue();
                int flagsF = field[i][j].getFlagsAround();

                if(field[i][j].getValue() < 9 && valueF != flagsF) {
                    if(valueF - flagsF == field[i][j].getClosedCells() - flagsF){
                        chances2(field[i][j]);
                    }
                }
            }
        }
    }
    public void chances2(SolverCell current){
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if(x >= 0 && y >= 0 && x < w && y < h && field[x][y].getValue() == 9 && !field[x][y].getChances().isEmpty()) {
                field[x][y].listClear();
                field[x][y].addChance(1);
                Minesweeper.chanceCur(x, y, 1);
            }
        }
    }


    public void correctionCl(){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if(!field[i][j].getChances().isEmpty()){
                    if(curChance > field[i][j].getChances().get(0)){
                        minChance = field[i][j];
                        curChance = field[i][j].getChances().get(0);
                    }
                    field[i][j].listClear();
                }
            }
        }
    }

    public void chancesMulti(SolverCell current, double numC){
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if(x >= 0 && y >= 0 && x < w && y < h && field[x][y].getValue() == 9 && !field[x][y].getChances().isEmpty()) {
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
            if(x >= 0 && y >= 0 && x < w && y < h && field[x][y].getValue() == 9 && !field[x][y].getChances().isEmpty()) {
               num += field[x][y].getChances().get(0);
            }
        }
        return num;
    }

    public void chanceForAll(SolverCell current, Double num){
        for(int i = 0 ; i < 8; i++ ) {
            int x = dev[i][0] + current.getX();
            int y = dev[i][1] + current.getY();
            if(x >= 0 && y >= 0 && x < w && y < h && field[x][y].getValue() == 9) {
                field[x][y].addChance(num);
            }
        }
    }

    public void openFirstOne(){
        breakingPoint:
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if(field[i][j].getValue() == 9){
                    open(i, j);
                    break breakingPoint;
                }
            }

        }
    }

    public void flagAll(){
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if(field[i][j].getValue() == 9){
                    field[i][j].setValue(10);
                    Minesweeper.flagCur(i, j);
                    openedCells--;
                    flagsOnMap++;
                    sayForFlag(field[i][j]);
                    changing = true;
                }
            }
        }
    }
}