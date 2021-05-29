import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class Game15 implements ActionListener{
    static int size = 300;
    ArrayList<JLabel> cells = new ArrayList<JLabel>();
    int zero;
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
        frame.addKeyListener(new MyKeyListener());
        panel.addMouseListener(new MyMouseListener());
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
        about.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(frame, "Лапушкина Александра Р3169 2021г.");
            }
        });
        about.addMenuKeyListener(new MenuKeyListener() {
            @Override
            public void menuKeyTyped(MenuKeyEvent e) {
               if (e.getKeyCode() == 0) {
                   JOptionPane.showMessageDialog(frame, "Лапушкина Александра Р3169 2021г.");
               }
            }

            @Override
            public void menuKeyPressed(MenuKeyEvent e) {

            }

            @Override
            public void menuKeyReleased(MenuKeyEvent e) {

            }
        });

        frame.setJMenuBar(bar);
    }

    //Отображение сетки
    private void drawGrid() {
        panel.removeAll();
        GridLayout grid = new GridLayout(4, 4);
        panel.setLayout(grid);
        for (JLabel x: cells) {
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
            swap(i, 15);
        }
        zero = 15;
    }

    private void swap(int x, int y){
        String temp = cells.get(x).getText();
        cells.get(x).setText(cells.get(y).getText());
        cells.get(y).setText(temp);
        drawGrid();
    }

    //Начать иновую игру
    private void newGame() {
        shuffle();
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

    public class MyKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch(key){
                case KeyEvent.VK_RIGHT:
                    if (zero % 4 > 0) {
                        int newZero = zero - 1;
                        swap(zero, newZero);
                        zero = newZero;
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (zero / 4 < 3) {
                        int newZero = zero + 4;
                        swap(zero, newZero);
                        zero = newZero;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (zero % 4 < 3) {
                        int newZero = zero + 1;
                        swap(zero, newZero);
                        zero = newZero;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (zero / 4 > 0) {
                        int newZero = zero - 4;
                        swap(zero, newZero);
                        zero = newZero;
                    }
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public class MyMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX() / (panel.getWidth() / 4);
            int y = e.getY() / (panel.getHeight() / 4);

            if (x == zero % 4 && Math.abs(y - zero / 4) == 1 || Math.abs(x - zero % 4) == 1 && y == zero / 4) {
                int newZero = y * 4 + x;
                swap(zero, newZero);
                zero = newZero;
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}


