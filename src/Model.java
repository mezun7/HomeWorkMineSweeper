import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: shuhrat
 * Date: 03.10.13
 * Time: 8:40
 * To change this template use File | Settings | File Templates.
 */
public class Model {
    private int flagsUsed, quantityOfMines;
    private int[][] field;
    private DFS dfs;
    private NewButton[][] buttons;
    public Model(NewButton[][] buttons) {
        GenerateField(buttons);
    }

    private void GenerateNumbers(int row, int column) {
        int[] xx = {1, -1, 0, 0, 1, 1, -1, -1};
        int[] yy = {0, 0, 1, -1, 1, -1, 1, -1};

        for(int i = 0; i < xx.length; i++) {
            int tmpY = yy[i] + row;
            int tmpX = xx[i] + column;
            if(tmpX < field.length && tmpX >= 0 && tmpY < field.length && tmpY >= 0) {
                if(field[tmpY][tmpX] != -1) {
                    field[tmpY][tmpX]++;
                }
            }
        }
    }

    public void GenerateField(NewButton[][] buttons) {
        flagsUsed = quantityOfMines = 10;
        field = new int[9][9];
        this.buttons = buttons;
        dfs = new DFS(field, buttons);

        for(int i = 0; i < field.length; i++) {
            Arrays.fill(field[i], 0);
        }

        for(int i = 0; i < 10; i++) {
            int row = new Random().nextInt(9);
            int column = new Random().nextInt(9);
            field[row][column] = -1;
            GenerateNumbers(row, column);
        }

    }

    public int getSum() {
        return dfs.getSum();
    }

    public  int getFlagsUsed() {
        return flagsUsed;
    }

    public void setFlagsUsed(int a) {
        flagsUsed += a;
    }

    public int getQuantityOfMines() {
        return quantityOfMines;
    }

    public ArrayList<Coordinates> getCellsToOpen(int row, int column) {
        if(field[row][column] == -1)
            return null;
        ArrayList<Coordinates> list;
        return  dfs.start(row, column);
    }

    @Override
    public String toString() {
        String s = new String();
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field.length; j++) {
                s += field[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }

}
