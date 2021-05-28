import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Game15 implements ActionListener{
    static int size = 300;
    ArrayList<Integer> cells = new ArrayList<Integer>();
    JFrame frame = new JFrame();

    public static void main(String[] args) {
        new Game15();

    }

    Game15() {
        for (int i = 0; i < 15; i++) { //Заполняем массив
            cells.add(i + 1);
        }
        cells.add(0);
        frame();
    }

    private void frame() {
        frame.setTitle("Пятнашки");
        frame.setSize(size, size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setJMenuBar(menu());
        frame.setLayout(grid());
        frame.setVisible(true);
    }

    private JMenuBar menu() {
        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File", true);
        JMenuItem newGame = new JMenuItem("New");
        JMenuItem exitGame = new JMenuItem("Exit");
        file.add(newGame);
        file.add(exitGame);

        JMenu about = new JMenu("About", true);

        bar.add(file);
        bar.add(about);

        newGame.addActionListener(this);
        exitGame.addActionListener(this);
        about.addActionListener(this);


        return bar;
    }

    private GridLayout grid() {
        GridLayout grid = new GridLayout(4, 4);
        for (int x: cells) {
                JLabel label = new JLabel(x == 0 ? "" : String.valueOf(x), SwingConstants.CENTER); //Не рисуем 0, центрируем текст
                label.setBorder(new LineBorder(Color.BLACK, 1));
                label.setFont(new Font("Arial", Font.PLAIN, size / 32 * 5));
                frame.add(label);
        }
        return grid;
    }

    private void newGame() { //перемешать массив
        new Game15();
        shuffle();
    }

    private void shuffle() {
        Collections.shuffle(cells);
        int temp = cells.get(15);
        if (temp != 0) {
            cells.set(cells.indexOf(0), temp);
            cells.set(15, 0);
        }
    }

    private void exitGame() { //перемешать массив
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        switch (command) {
            case "New" :
               newGame();
               break;
            case "Exit" :
                exitGame();
                break;
            case "Menu" :
                break;
        }
    }

}
