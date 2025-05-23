package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GomokuGameUI extends JFrame {
    private final GameBoard board;
    private final GameRule rule;
    private final Player player1, player2;
    private Player currentPlayer;
    private boolean gameOver = false;

    private final int SIZE = 15;
    private final int CELL_SIZE = 40;

    public GomokuGameUI(GameFactory factory) {
        this.board = factory.createBoard();
        this.rule = factory.createRule();
        this.player1 = factory.createPlayer("玩家 1", "X");
        this.player2 = factory.createPlayer("玩家 2", "O");
        this.currentPlayer = player1;

        setTitle("五子棋（畫布風格 + 抽象工廠）");
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
                    if (((GomokuBoard) board).getBoardState()[row][col].equals(".")) {
                        board.placePiece(row, col, currentPlayer.side);
                        panel.repaint();

                        if (rule.checkWin(((GomokuBoard) board).getBoardState(), currentPlayer.side)) {
                            JOptionPane.showMessageDialog(GomokuGameUI.this,
                                    currentPlayer.name + " (" + currentPlayer.side + ") 勝利！");
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

    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 背景顏色
            g.setColor(new Color(240, 200, 120));
            g.fillRect(0, 0, getWidth(), getHeight());

            // 畫線
            g.setColor(Color.BLACK);
            for (int i = 0; i < SIZE; i++) {
                g.drawLine(CELL_SIZE / 2, CELL_SIZE / 2 + i * CELL_SIZE,
                        CELL_SIZE / 2 + (SIZE - 1) * CELL_SIZE, CELL_SIZE / 2 + i * CELL_SIZE);
                g.drawLine(CELL_SIZE / 2 + i * CELL_SIZE, CELL_SIZE / 2,
                        CELL_SIZE / 2 + i * CELL_SIZE, CELL_SIZE / 2 + (SIZE - 1) * CELL_SIZE);
            }

            // 星位
            int[] stars = {3, 7, 11};
            for (int r : stars)
                for (int c : stars)
                    g.fillOval(c * CELL_SIZE + CELL_SIZE / 2 - 3, r * CELL_SIZE + CELL_SIZE / 2 - 3, 6, 6);

            // 棋子
            String[][] state = ((GomokuBoard) board).getBoardState();
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
}
