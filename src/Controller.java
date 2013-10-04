import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: shuhrat
 * Date: 03.10.13
 * Time: 8:39
 * To change this template use File | Settings | File Templates.
 */


public class Controller {

    public static void main(String[] args) {
        NewButton[][] buttons = new NewButton[9][9];
        Model model = new Model(buttons);
        System.out.println(model);
        View view = new View(model, buttons);

        //ArrayList<Coordinates> list = model.getCellsToOpen(3, 1);
        //Iterator<Coordinates> iter = list.iterator();
        //System.out.println(model + "\n List:\n");

       /*
       while(iter.hasNext()) {
            Coordinates tt = (Coordinates) iter.next();
            System.out.println("row: " + tt.row + "column: " + tt.column + "value: " + tt.value);
        }
        */

    }
}
