import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class GeneratorTest {
    private static Generator test;

    @BeforeAll
    static void setUp(){
        test = new Generator(3,3,3, false);
        test.generate();

    }

    @Test
    void generate() {
        Polygon[][] cells = test.info;

        Assertions.assertEquals(9, cells[0].length + cells[1].length + cells[2].length );
    }

    @org.junit.jupiter.api.Test
    void planting() {
        test = new Generator(3,3,3,false);
        test.generate();
        int countMines = 0;
        test.planting();
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (test.info[i][j].getMine()) countMines++;
            }
        }
        Assertions.assertEquals(3, countMines);
    }

    @org.junit.jupiter.api.Test
    void numberB() {
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