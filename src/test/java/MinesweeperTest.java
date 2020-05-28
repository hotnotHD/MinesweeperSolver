import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MinesweeperTest {
    private static Generator test;

    public int mineCount(Generator field){
        int countMines = 0;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (field.info[i][j].getMine()) countMines++;
            }
        }
        return countMines;
    }

    public void fastPlant(Generator field){
        field.generate();
        field.planting();
    }

    @Test
    void generate() {
        test = new Generator(3,3,3, false);
        test.generate();
        Hexagon[][] cells = test.info;
        Assertions.assertEquals(9, cells[0].length + cells[1].length + cells[2].length );
        Assertions.assertEquals(3, cells[0].length);
        Assertions.assertEquals(3, cells[1].length);
        Assertions.assertEquals(3, cells[2].length);
    }

    @org.junit.jupiter.api.Test
    void planting() {
        test = new Generator(3,3,3,false);
        fastPlant(test);
        Assertions.assertEquals(3, mineCount(test));
        test = new Generator(4,3,3,false);
        fastPlant(test);
        Assertions.assertEquals(4, mineCount(test));
        test = new Generator(20,3,3,false);
        fastPlant(test);
        Assertions.assertEquals(8, mineCount(test));
        test = new Generator(20,3,3,false);
        test.generate();
        test.info[0][0].open(null);
        test.planting();
        Assertions.assertEquals(8, mineCount(test));
    }

    @org.junit.jupiter.api.Test
    void numberB() {
        test = new Generator(3,3,3, false);
        test.generate();
        test.info[0][0].plantBomb();
        test.info[2][2].plantBomb();
        test.info[1][2].plantBomb();
        test.info[1][0].plantBomb();
        test.numberB();
        Assertions.assertEquals(true,test.info[0][0].getMine());
        Assertions.assertEquals(2,test.info[0][1].getNumberB());
        Assertions.assertEquals(1,test.info[0][2].getNumberB());
        Assertions.assertEquals(true,test.info[1][0].getMine());
        Assertions.assertEquals(3,test.info[1][1].getNumberB());
        Assertions.assertEquals(true,test.info[1][2].getMine());
        Assertions.assertEquals(1,test.info[2][0].getNumberB());
        Assertions.assertEquals(2,test.info[2][1].getNumberB());
        Assertions.assertEquals(true,test.info[2][2].getMine());
    }

}