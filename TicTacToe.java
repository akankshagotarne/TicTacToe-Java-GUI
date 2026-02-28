import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {

    private JButton[][] buttons = new JButton[3][3];
    private JLabel statusLabel;
    private JLabel scoreLabel;

    private char currentPlayer = 'X';
    private int scoreX = 0;
    private int scoreO = 0;

    public TicTacToe() {
        setTitle("Tic Tac Toe - GUI Version");
        setSize(400, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // Top Panel (Scoreboard)
        JPanel topPanel = new JPanel();
        scoreLabel = new JLabel("Score  X: 0  |  O: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(scoreLabel);

        // Center Panel (Game Board)
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        Font buttonFont = new Font("Arial", Font.BOLD, 60);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(buttonFont);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                boardPanel.add(buttons[i][j]);
            }
        }

        // Bottom Panel (Status + Restart)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));

        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton restartButton = new JButton("Restart Game");

        restartButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to restart the game?",
                    "Restart Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                resetBoard();
            }
        });

        bottomPanel.add(statusLabel);
        bottomPanel.add(restartButton);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (!clickedButton.getText().equals("")) {
            return;
        }

        clickedButton.setText(String.valueOf(currentPlayer));

        if (checkWin()) {
            if (currentPlayer == 'X') {
                scoreX++;
            } else {
                scoreO++;
            }

            updateScore();
            statusLabel.setText("Player " + currentPlayer + " Wins!");
            disableButtons();

        } else if (isBoardFull()) {
            statusLabel.setText("It's a Draw!");
            disableButtons();  // Improved: Disable board on draw
        } else {
            switchPlayer();
            statusLabel.setText("Player " + currentPlayer + "'s Turn");
        }
    }

    private boolean checkWin() {

        // Rows and Columns
        for (int i = 0; i < 3; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2]))
                return true;

            if (checkLine(buttons[0][i], buttons[1][i], buttons[2][i]))
                return true;
        }

        // Diagonals
        if (checkLine(buttons[0][0], buttons[1][1], buttons[2][2]))
            return true;

        if (checkLine(buttons[0][2], buttons[1][1], buttons[2][0]))
            return true;

        return false;
    }

    private boolean checkLine(JButton b1, JButton b2, JButton b3) {

        if (!b1.getText().equals("") &&
                b1.getText().equals(b2.getText()) &&
                b2.getText().equals(b3.getText())) {

            b1.setBackground(Color.GREEN);
            b2.setBackground(Color.GREEN);
            b3.setBackground(Color.GREEN);
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().equals(""))
                    return false;
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(null);
            }

        currentPlayer = 'X';
        statusLabel.setText("Player X's Turn");
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setEnabled(false);
    }

    private void updateScore() {
        scoreLabel.setText("Score  X: " + scoreX + "  |  O: " + scoreO);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToe());
    }
}