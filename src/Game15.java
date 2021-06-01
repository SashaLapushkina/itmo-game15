import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class Game15 {
    private final int size = 400;
    private ArrayList<JLabel> cells = new ArrayList<>(); //Массив костей
    private int zero; //Позиция пустой кости
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();

    public static void main(String[] args) {
        new Game15();
}

    Game15() {
        //Создаем окно
        frame.setTitle("Пятнашки");
        frame.setSize(size, size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //Отрисовываем меню
        drawMenu();

        //Заполняем массив
        for (int i = 0; i < 16; i++) {
            cells.add(new JLabel(String.valueOf(i + 1), SwingConstants.CENTER));
            cells.get(i).setBorder(new LineBorder(Color.BLACK, 1));
            cells.get(i).setFont(new Font("Arial", Font.PLAIN, size / 32 * 5));
        }
        cells.get(15).setText("");

        frame.add(panel);
        //Реагируем на нажатие мыши
        panel.addMouseListener(new MouseAdapter() {
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
        });

        //Реагируем на нажатие стрелок
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_RIGHT:
                        if (zero % 4 > 0) {
                            swapZero(zero - 1);
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (zero / 4 < 3) {
                            swapZero(zero + 4);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (zero % 4 < 3) {
                            swapZero(zero + 1);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (zero / 4 > 0) {
                            swapZero(zero - 4);
                        }
                        break;
                }
                drawGrid();
            }
        });

        //Запускаем новую игру
        newGame();

        frame.setVisible(true);
    }

    //Отображение меню
    private void drawMenu() {
        //Создаем панель меню
        JMenuBar bar = new JMenuBar();

        //Создаем выпадающее меню
        JMenu file = new JMenu("File", true);
        file.setMnemonic(KeyEvent.VK_F);

        //Создаем пункты выпадающего меню
        JMenuItem newGame = new JMenuItem("New");
        newGame.setMnemonic(KeyEvent.VK_N);
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));

        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.setMnemonic(KeyEvent.VK_E);
        exitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));

        //Добавляем пункты выпадающего меню
        file.add(newGame);
        file.addSeparator();
        file.add(exitGame);

        //Создаем кнопку About
        JMenu about = new JMenu("About");
        about.setMnemonic(KeyEvent.VK_A);

        //Добавляем пункты меню на панель
        bar.add(file);
        bar.add(about);

        //Вешаем слушатели на выпадающее меню
        newGame.addActionListener(e -> newGame());
        exitGame.addActionListener(e -> System.exit(0));

        //Делаем кликабельным пункт About
        about.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(frame, "Лапушкина Александра Р3169 2021г.", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //Добавляем возможность активировать пункт About клавишей enter
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

        //Добавляем меню
        frame.setJMenuBar(bar);
    }

    //Отображение сетки
    private void drawGrid() {
        //Очищаем панель
        panel.removeAll();

        //Добавляем сетку
        GridLayout grid = new GridLayout(4, 4);
        panel.setLayout(grid);

        //Добавляем пункты
        for (JLabel x : cells) {
            panel.add(x);
        }
    }

    //Перемешать элементы в массиве
    private void shuffle() {
        Collections.shuffle(cells);
        int i = 0;

        //Ставим 0 пустую кость на последнюю позицию
        if (!cells.get(15).getText().equals("")) {
            while (i < 15 && !cells.get(i).getText().equals("")) {
                i++;
            }
            swap(i, 15);
        }
        zero = 15;
    }

    //Поменять местами две ячейки
    private void swap(int x, int y) {
        String temp = cells.get(x).getText();
        cells.get(x).setText(cells.get(y).getText());
        cells.get(y).setText(temp);
    }

    //Поменять местами пустую ячейку с данной
    private void swapZero(int x) {
        cells.get(zero).setText(cells.get(x).getText());
        cells.get(x).setText("");
        zero = x;

        //Проверяем, решена ли головоломка
        if (isSolved()) {
            JOptionPane.showMessageDialog(frame, "Ура! Пятнашка собрана!","Победа", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //Начать иновую игру
    private void newGame() {
        shuffle();
        drawGrid();
    }

    //Проверка, решена ли головоломка
    private boolean isSolved() {
        int i = 0;
        while (i < 14 && !cells.get(i).getText().equals("") && !cells.get(i + 1).getText().equals("") && Integer.parseInt(cells.get(i).getText()) < Integer.parseInt(cells.get(i + 1).getText())) {
            i++;
        }
        return  i == 14;
    }
}
