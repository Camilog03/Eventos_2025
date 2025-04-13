import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame extends JFrame {
    private final int GRID_SIZE = 4; // Tamaño del tablero (4x4)
    private JButton[][] buttons;
    private int[][] numbers;
    private boolean firstSelection = true;
    private JButton firstButton;
    private int firstNumber;

    public MemoryGame() {
        setTitle("Juego de Memoria - Encuentra la Pareja");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        setSize(400, 400);

        buttons = new JButton[GRID_SIZE][GRID_SIZE];
        numbers = new int[GRID_SIZE][GRID_SIZE];

        // Generar números aleatorios emparejados
        ArrayList<Integer> numberList = new ArrayList<>();
        for (int i = 1; i <= (GRID_SIZE * GRID_SIZE) / 2; i++) {
            numberList.add(i);
            numberList.add(i);
        }
        Collections.shuffle(numberList);

        // Asignar los números al tablero
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                numbers[i][j] = numberList.remove(0);
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
            }
        }

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = buttons[row][col];
            clickedButton.setText(String.valueOf(numbers[row][col]));
            clickedButton.setEnabled(false);

            if (firstSelection) {
                firstSelection = false;
                firstButton = clickedButton;
                firstNumber = numbers[row][col];
            } else {
                if (numbers[row][col] == firstNumber) {
                    // Pareja encontrada
                    JOptionPane.showMessageDialog(null, "¡Pareja encontrada!");
                } else {
                    // No coincide, ocultar ambos números manualmente
                    JOptionPane.showMessageDialog(null, "¡No coinciden!");
                    clickedButton.setText("");
                    clickedButton.setEnabled(true);
                    firstButton.setText("");
                    firstButton.setEnabled(true);
                }
                firstSelection = true;
            }
        }
    }

    public static void main(String[] args) {
        new MemoryGame();
    }
}