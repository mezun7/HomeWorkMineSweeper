import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: shuhrat
 * Date: 03.10.13
 * Time: 8:58
 * To change this template use File | Settings | File Templates.
 */
public class DFS {
    private int[][] field;
    private boolean[][] used;
    private ArrayList<Coordinates> list;
    private int[] xx = {1, -1, 0, 0, 1, 1, -1, -1};
    private int[] yy = {0, 0, 1, -1, 1, -1, 1, -1};
    private int sum;
    private NewButton[][] buttons;
    public DFS(int[][] field, NewButton[][] buttons) {
        this.field = field;
        used = new boolean[field.length][field.length];
        for(int i = 0; i < used.length; i++) {
            Arrays.fill(used[i], true);
            sum = 0;
        }
        this.buttons = buttons;
    }

    private void dfs(int row, int column) {
        used[row][column] = false;

        for(int i = 0; i < xx.length; i++) {
            int tmpY = row + yy[i];
            int tmpX = column + xx[i];
            if(tmpX < field.length && tmpY < field.length && tmpX >= 0 && tmpY >= 0 && used[tmpY][tmpX] && !buttons[tmpY][tmpX].isFlagged) {
                if(field[tmpY][tmpX] == 0) {
                    //sum++;
                    list.add(new Coordinates(tmpY, tmpX, field[tmpY][tmpX]));
                    dfs(tmpY, tmpX);
                } else if(field[tmpY][tmpX] > 0) {
                    //sum++;
                    list.add(new Coordinates(tmpY, tmpX, field[tmpY][tmpX]));
                }
            }
        }

    }

    public int getSum() {
        return sum;
    }

    public ArrayList<Coordinates> start(int row, int column) {
        list = new ArrayList<Coordinates>();
        dfs(row, column);
        sum++;
        list.add(new Coordinates(row, column, field[row][column]));
        if(field[row][column] == -1) {
            return null;
        } else {
            sum += list.size();
        }

        return list;
    }
}
