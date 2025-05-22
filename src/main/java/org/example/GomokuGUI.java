package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GomokuGUI extends JFrame {
    private final int SIZE = 15;
    private final int CELL_SIZE = 40;
    private final char[][] board = new char[SIZE][SIZE];
    private boolean turnX = true;
    private boolean gameOver = false;

    public GomokuGUI() {
        setTitle("五子棋遊戲 Gomoku");
        setSize(SIZE * CELL_SIZE + 40, SIZE * CELL_SIZE + 60);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        for (char[] row : board) {
            java.util.Arrays.fill(row, '.');
        }

        GamePanel panel = new GamePanel();
        panel.setPreferredSize(new Dimension(SIZE * CELL_SIZE, SIZE * CELL_SIZE));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameOver) return;

                int row = e.getY() / CELL_SIZE;
                int col = e.getX() / CELL_SIZE;

                if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] == '.') {
                    board[row][col] = turnX ? 'X' : 'O';
                    panel.repaint();

                    if (checkWinner(row, col)) {
                        String winner = turnX ? "玩家 X" : "玩家 O";
                        JOptionPane.showMessageDialog(GomokuGUI.this, winner + " 獲勝！");
                        gameOver = true;
                    } else {
                        turnX = !turnX;
                    }
                }
            }
        });

        add(panel);
        pack();
        setVisible(true);
    }

    private boolean checkWinner(int row, int col) {
        char symbol = board[row][col];
        return countInDirection(row, col, 0, 1, symbol) + countInDirection(row, col, 0, -1, symbol) >= 4 ||
                countInDirection(row, col, 1, 0, symbol) + countInDirection(row, col, -1, 0, symbol) >= 4 ||
                countInDirection(row, col, 1, 1, symbol) + countInDirection(row, col, -1, -1, symbol) >= 4 ||
                countInDirection(row, col, 1, -1, symbol) + countInDirection(row, col, -1, 1, symbol) >= 4;
    }

    private int countInDirection(int row, int col, int dRow, int dCol, char symbol) {
        int count = 0;
        for (int i = 1; i < 5; i++) {
            int r = row + dRow * i;
            int c = col + dCol * i;
            if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c] == symbol) {
                count++;
            } else break;
        }
        return count;
    }

    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 背景
            g.setColor(new Color(240, 200, 120));
            g.fillRect(0, 0, getWidth(), getHeight());

            // 畫棋盤線
            g.setColor(Color.BLACK);
            for (int i = 0; i < SIZE; i++) {
                g.drawLine(CELL_SIZE / 2, CELL_SIZE / 2 + i * CELL_SIZE,
                        CELL_SIZE / 2 + (SIZE - 1) * CELL_SIZE, CELL_SIZE / 2 + i * CELL_SIZE);
                g.drawLine(CELL_SIZE / 2 + i * CELL_SIZE, CELL_SIZE / 2,
                        CELL_SIZE / 2 + i * CELL_SIZE, CELL_SIZE / 2 + (SIZE - 1) * CELL_SIZE);
            }

            // 畫棋子
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if (board[row][col] == 'X') {
                        g.setColor(Color.BLACK);
                        g.fillOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                    } else if (board[row][col] == 'O') {
                        g.setColor(Color.WHITE);
                        g.fillOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                        g.setColor(Color.BLACK);
                        g.drawOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GomokuGUI::new);
    }
}

