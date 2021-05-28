import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.MenuDragMouseListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class Game15 implements ActionListener{
    static int size = 300;
    ArrayList<JLabel> cells = new ArrayList<JLabel>();
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    public static void main(String[] args) {
        new Game15();
    }

    Game15() {
        frame.setTitle("Пятнашки");
        frame.setSize(size, size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        drawMenu();

        for (int i = 0; i < 16; i++) { //Заполняем массив
            cells.add(new JLabel(String.valueOf(i + 1), SwingConstants.CENTER));
            cells.get(i).setBorder(new LineBorder(Color.BLACK, 1));
            cells.get(i).setFont(new Font("Arial", Font.PLAIN, size / 32 * 5));
        }
        cells.get(15).setText("");

        newGame();

        frame.setVisible(true);
    }

    //Отображение меню
    private void drawMenu() {
        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File", true);
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem newGame = new JMenuItem("New");
        newGame.setMnemonic(KeyEvent.VK_N);
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));

        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.setMnemonic(KeyEvent.VK_E);
        exitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));

        file.add(newGame);
        file.addSeparator();
        file.add(exitGame);

        JMenu about = new JMenu("About");
        about.setMnemonic(KeyEvent.VK_A);

        bar.add(file);
        bar.add(about);

        newGame.addActionListener(this);
        exitGame.addActionListener(this);
        about.addActionListener(this);

        frame.setJMenuBar(bar);
    }

    //Отображение сетки
    private void drawGrid() {
        panel.removeAll();
        GridLayout grid = new GridLayout(4, 4);
        panel.setLayout(grid);
        for (JLabel x: cells) {
                JLabel label = new JLabel(x.getText(), SwingConstants.CENTER);
                panel.add(x);
        }
    }

    //Перемешать элементы в массиве
    private void shuffle() {
        Collections.shuffle(cells);
        int i = 0;
        if (cells.get(15).getText() != "") {
            String str = cells.get(15).getText();
            while (i < 15 && cells.get(i).getText() != "") {
                i++;
            }
            cells.get(i).setText(cells.get(15).getText());
            cells.get(15).setText("");
        }
    }

    //Начать иновую игру
    private void newGame() {
        shuffle();
        drawGrid();
    }

    private void exitGame() { //перемешать массив
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New" :
               newGame();
               break;
            case "Exit" :
                exitGame();
                break;
            case "About" :
                JOptionPane.showMessageDialog(frame, "Лапушкина Александра Р3169 2021г.");
                break;
        }
    }
}
