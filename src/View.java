import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created with IntelliJ IDEA.
 * User: shuhrat
 * Date: 03.10.13
 * Time: 9:30
 * To change this template use File | Settings | File Templates.
 */
public class View {
    private Model model;
    private NewButton[][] buttons;
    public View(Model model, NewButton[][] buttons) {
        this.model = model;
        this.buttons = buttons;
        GUI gui = new GUI(model, buttons);

    }


}

class GUI extends JFrame {
    private JPanel jPanel;
    private JMenuBar jMenuBar;
    private JMenu jMenuFlagsUsed;
    private Model model;
    private NewButton[][] buttons;

    GUI(Model model, NewButton[][] buttons) {
        this.model = model;
        this.buttons = buttons;
        this.setJMenuBar(gtMenuBar());
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.add(gtjPanel());
        this.setSize(300, 300);
        this.setEnabled(true);
        this.setVisible(true);
        this.setTitle("MineSweeper 2");


    }

    private JMenuBar gtMenuBar() {
        jMenuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("Main menu");
        jMenuBar.add(mainMenu);
        jMenuFlagsUsed = new JMenu("Flags Remaining: " + model.getFlagsUsed());
        jMenuBar.setBorderPainted(true);
        jMenuBar.add(jMenuFlagsUsed);

        JMenu qquit = new JMenu("Quit");
        qquit.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(SwingUtilities.isLeftMouseButton(e)) {
                    System.exit(0);
                }
            }
        });
        mainMenu.add(qquit);
        return jMenuBar;
    }

    private JPanel gtjPanel() {
        jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(9, 9));
        for(int i = 0; i < buttons.length; i++) {
            for(int j = 0; j < buttons.length; j++) {
                buttons[i][j] = new NewButton(model, i, j,jMenuFlagsUsed, buttons, this);
                jPanel.add(buttons[i][j]);
            }
        }

        return jPanel;
    }


}

class IconButton {
    private static Icon flag = new ImageIcon("flag.png");
    private static Icon[] icons = new Icon[8];
    private static Icon mineFault = new ImageIcon("minefault.png");
    private static Icon mine = new ImageIcon("mine.png");


    static Icon getFlag() {
        return flag;
    }

    static void setFlag(Icon flag) {
        IconButton.flag = flag;
    }

    static Icon getIcons(int i) {
        //System.out.println(i + ".png");
        return new ImageIcon(i + ".png");
    }


    static Icon getMineFault() {
        return mineFault;
    }

    static void setMineFault(Icon mineFault) {
        IconButton.mineFault = mineFault;
    }

    static Icon getMine() {
        return mine;
    }

    static void setMine(Icon mine) {
        IconButton.mine = mine;
    }
}

class NewButton extends JButton {
    private Model model;
    private Coordinates coord;
    private JMenu jMenuFlagsUsed;
    boolean isFlagged = false;
    private NewButton[][] buttons;
    private GUI gui;
    public NewButton(Model model, int row, int column, JMenu jMenuFlagsUsed, NewButton[][] buttons, GUI gui) {
        this.model = model;
        this.jMenuFlagsUsed = jMenuFlagsUsed;
        coord = new Coordinates(row, column, -2);
        setListnerToListenTo();
        this.buttons = buttons;
        this.gui = gui;
    }

    private void check() {
        int sum = 0;
        int sum2 = 0;
        for(int i = 0; i < buttons.length; i++) {
            for(int j = 0; j < buttons.length; j++) {
                sum += (buttons[i][j].isEnabled()) ? 0 : 1;
                sum2 += (buttons[i][j].isFlagged) ? 1 : 0;
            }
        }
        System.out.println(sum + sum2);
        if(sum == 81 - 10 && sum2 == 10) {
            Object[] options = {"OK",
                    "Выйти"};
            int n = JOptionPane.showOptionDialog(gui,
                    "Поздравляем! Вы выйграли!",
                    "Поздравляем!",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options, options[1]);
            if(n == 1) {
                System.exit(0);
            } else {
                gui.removeAll();

                gui.revalidate();
                gui.repaint();

                buttons = new NewButton[9][9];
                model.GenerateField(buttons);
                //gui.setEnabled(false);
            }
        }
    }

    private void LKMAction() {
        if(!isFlagged) {
            ArrayList<Coordinates> list = model.getCellsToOpen(coord.row, coord.column);
            if(list != null) {
                Iterator iterator = list.iterator();

                while(iterator.hasNext()) {
                    Coordinates tmp = (Coordinates)iterator.next();
                    if(tmp.value == 0) {
                        buttons[tmp.row][tmp.column].setIcon(new ImageIcon("background.png"));
                        buttons[tmp.row][tmp.column].setEnabled(false);
                        //System.out.println((buttons == null) ? 1 : 2);
                    } else {
                        //System.out.println((buttons == null) ? 1 : 2);
                        buttons[tmp.row][tmp.column].setIcon(IconButton.getIcons(tmp.value));
                        buttons[tmp.row][tmp.column].setEnabled(false);


                    }
                }
                check();

            } else {
                System.out.println("oops!");
                this.setEnabled(false);
                this.setIcon(new ImageIcon("minefault.png"));
                Object[] options = {"OK",
                        "Выйти"};
                int n = JOptionPane.showOptionDialog(gui,
                        "Вы проиграли!",
                        "Проигрыш!",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options, options[1]);
                if(n == 1) {
                    System.exit(0);
                } else {
                    gui.removeAll();
                    gui.revalidate();
                    gui.repaint();
                    buttons = new NewButton[9][9];
                    model.GenerateField(buttons);
                }
            }
        }
    }

    private void RKMAction() {
        if(isFlagged) {
            this.setIcon(null);
            model.setFlagsUsed(1);
            isFlagged = false;
        } else if (this.isEnabled() && !isFlagged && model.getFlagsUsed() -1 >= 0) {
            model.setFlagsUsed(-1);
            this.setIcon(IconButton.getFlag());
            isFlagged = true;
        }
        this.jMenuFlagsUsed.setText("Flags remaining: " + model.getFlagsUsed());
        //int sum = 0;
        check();

    }

    private void setListnerToListenTo() {
        this.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(SwingUtilities.isLeftMouseButton(e)) {
                    LKMAction();
                } else if(SwingUtilities.isRightMouseButton(e)) {
                    RKMAction();
                }
            }
        });
    }


}


