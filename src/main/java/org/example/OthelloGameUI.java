package org.example;

// --- Swing UI ---

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//OthelloGameUI 是黑白棋的圖形介面。
//使用抽象工廠模式建立遊戲元件，並用 Swing 呈現棋盤與遊戲互動
public class OthelloGameUI extends JFrame {
    private final GameBoard board;
    private final OthelloRule rule; // 強制使用 OthelloRule 的擴充功能
    private final Player player1, player2;
    private Player currentPlayer;
    private boolean gameOver = false;

    private final int SIZE = 8;
    private final int CELL_SIZE = 60;   // 每格 60px

    public OthelloGameUI(GameFactory factory) {
        this.board = factory.createBoard();
        this.rule = (OthelloRule) factory.createRule(); // 向下轉型取得進階功能
        this.player1 = factory.createPlayer("玩家 1", "X");
        this.player2 = factory.createPlayer("玩家 2", "O");
        this.currentPlayer = player1;

        setTitle("黑白棋 Othello（抽象工廠 + Swing）");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(SIZE * CELL_SIZE + 40, SIZE * CELL_SIZE + 60);
        setLocationRelativeTo(null);   // 視窗置中
        setResizable(false);

        GamePanel panel = new GamePanel();
        panel.setPreferredSize(new Dimension(SIZE * CELL_SIZE, SIZE * CELL_SIZE));
        // 滑鼠點擊事件：處理落子與換手邏輯
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameOver) return;

                int row = e.getY() / CELL_SIZE;
                int col = e.getX() / CELL_SIZE;

                if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
                    // 若合法落子則更新畫面與進行換手或判斷結束
                    if (board.placePiece(row, col, currentPlayer.side)) {
                        panel.repaint();

                        // 換手
                        switchTurnOrEnd(panel);
                    }
                }
            }
        });

        add(panel);
        pack();
        setVisible(true);
    }

    // 判斷是否該換手、顯示結果或結束遊戲
    private void switchTurnOrEnd(JPanel panel) {
        Player opponent = (currentPlayer == player1) ? player2 : player1;
        String[][] state = ((OthelloBoard) board).getBoardState();

        boolean opponentCanMove = rule.hasAnyValidMove(state, opponent.side);
        boolean currentCanMove = rule.hasAnyValidMove(state, currentPlayer.side);

        if (!currentCanMove && !opponentCanMove) {
            // 雙方都無法行動 → 遊戲結束，顯示結果
            gameOver = true;
            panel.repaint();
            int black = rule.countPieces(state, "X");
            int white = rule.countPieces(state, "O");
            String result = "黑子: " + black + " 白子: " + white + "\n";

            switch (rule.getWinner(state)) {
                case "X": result += "黑子勝利！"; break;
                case "O": result += "白子勝利！"; break;
                default:  result += "平手！"; break;
            }

            JOptionPane.showMessageDialog(OthelloGameUI.this, result);
        } else if (opponentCanMove) {
            // 對手可以下，換對手
            currentPlayer = opponent;
        } else {
            // 對手無法下，提示當前玩家繼續
            JOptionPane.showMessageDialog(this, currentPlayer.side + " 繼續，對手無合法步！");
        }
    }

    // 遊戲畫面面板（棋盤繪製與棋子顯示）
    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 畫背景（綠色棋盤）
            g.setColor(new Color(34, 139, 34));
            g.fillRect(0, 0, getWidth(), getHeight());

            // 畫棋盤線
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
}
