package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OthelloGameUI extends JFrame {
    private final GameBoard board;
    private final GameRule rule;
    private final Player player1, player2;
    private Player currentPlayer;
    private boolean gameOver = false;

    private final int SIZE = 8;
    private final int CELL_SIZE = 60;

    public OthelloGameUI(GameFactory factory) {
        this.board = factory.createBoard();
        this.rule = factory.createRule();
        this.player1 = factory.createPlayer("玩家 1", "X");
        this.player2 = factory.createPlayer("玩家 2", "O");
        this.currentPlayer = player1;

        setTitle("黑白棋 Othello（抽象工廠 + Swing）");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SIZE * CELL_SIZE + 40, SIZE * CELL_SIZE + 60);
        setLocationRelativeTo(null);
        setResizable(false);

        GamePanel panel = new GamePanel();
        panel.setPreferredSize(new Dimension(SIZE * CELL_SIZE, SIZE * CELL_SIZE));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameOver) return;

                int row = e.getY() / CELL_SIZE;
                int col = e.getX() / CELL_SIZE;

                if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
                    if (board.placePiece(row, col, currentPlayer.side)) {
                        panel.repaint();

                        if (rule.checkWin(((OthelloBoard) board).getBoardState(), currentPlayer.side)) {
                            int black = countDiscs("X");
                            int white = countDiscs("O");
                            String result = "黑子: " + black + " 白子: " + white + "\n";

                            if (black > white)
                                result += "黑子勝利！";
                            else if (white > black)
                                result += "白子勝利！";
                            else
                                result += "平手！";

                            JOptionPane.showMessageDialog(OthelloGameUI.this, result);
                            gameOver = true;
                        } else {
                            currentPlayer = (currentPlayer == player1) ? player2 : player1;
                        }
                    }
                }
            }
        });

        add(panel);
        pack();
        setVisible(true);
    }

    private int countDiscs(String side) {
        int count = 0;
        String[][] state = ((OthelloBoard) board).getBoardState();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (state[i][j].equals(side)) count++;
        return count;
    }

    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 背景
            g.setColor(new Color(34, 139, 34)); // 綠色桌面
            g.fillRect(0, 0, getWidth(), getHeight());

            // 畫格線
            g.setColor(Color.BLACK);
            for (int i = 0; i <= SIZE; i++) {
                g.drawLine(0, i * CELL_SIZE, SIZE * CELL_SIZE, i * CELL_SIZE);
                g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, SIZE * CELL_SIZE);
            }

            // 畫棋子
            String[][] state = ((OthelloBoard) board).getBoardState();
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if (state[row][col].equals("X")) {
                        g.setColor(Color.BLACK);
                        g.fillOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                    } else if (state[row][col].equals("O")) {
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
        SwingUtilities.invokeLater(() -> {
            GameFactory factory = new OthelloFactory();
            new OthelloGameUI(factory);
        });
    }
}

