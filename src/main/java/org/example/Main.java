package org.example;

import java.util.*;

abstract class AbstractGame {
    abstract void setPlayers(Player p1, Player p2);
    abstract boolean gameOver();
    abstract boolean move(int row, int col);
}

class Player {
    String name;
    char symbol; // 'X' or 'O'

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }
}

class GomokuGame extends AbstractGame {
    private final int SIZE = 15;
    private char[][] board = new char[SIZE][SIZE];
    private Player player1, player2;
    private boolean turnX = true;

    public GomokuGame() {
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
    }

    @Override
    public void setPlayers(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    public void showBoard() {
        System.out.print("   ");
        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2d", i);
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%2c", board[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public boolean move(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            System.out.println("超出棋盤範圍！");
            return false;
        }

        if (board[row][col] != '.') {
            System.out.println("這個位置已經有棋子了！");
            return false;
        }

        char symbol = turnX ? player1.symbol : player2.symbol;
        board[row][col] = symbol;

        turnX = !turnX;
        return true;
    }

    @Override
    public boolean gameOver() {
        return checkWinner('X') || checkWinner('O');
    }

    private boolean checkWinner(char symbol) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == symbol) {
                    if (checkDirection(i, j, 1, 0, symbol) || // 橫向
                            checkDirection(i, j, 0, 1, symbol) || // 直向
                            checkDirection(i, j, 1, 1, symbol) || // 斜右下
                            checkDirection(i, j, 1, -1, symbol))  // 斜左下
                    {
                        System.out.println("玩家 " + (symbol == player1.symbol ? player1.name : player2.name) + " 獲勝！");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int dRow, int dCol, char symbol) {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            int r = row + dRow * i;
            int c = col + dCol * i;
            if (r < 0 || r >= SIZE || c < 0 || c >= SIZE || board[r][c] != symbol) {
                return false;
            }
        }
        return true;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (!gameOver()) {
            showBoard();
            Player current = turnX ? player1 : player2;
            System.out.println("輪到 " + current.name + " (" + current.symbol + ") 下棋：");

            System.out.print("請輸入 row col (例如 7 7): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (!move(row, col)) {
                System.out.println("請重新輸入合法位置。");
            }
        }

        showBoard();
        System.out.println("遊戲結束！");
    }
}

// 主方法
public class Main {
    public static void main(String[] args) {
        GomokuGame game = new GomokuGame();
        game.setPlayers(new Player("玩家1", 'X'), new Player("玩家2", 'O'));
        game.play();
    }
}
