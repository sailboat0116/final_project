package org.example;

// --- Othello 實作 ---

public class OthelloBoard implements GameBoard {
    private final int SIZE = 8;   // 黑白棋棋盤為 8x8
    private final String[][] board = new String[SIZE][SIZE];

    public OthelloBoard() {
        reset();
    }   // 建構時初始化棋盤

    @Override
    public void reset() {
        // 清空棋盤
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = ".";

        // 設定黑白棋初始四子位置（中心）
        board[3][3] = "O";
        board[3][4] = "X";
        board[4][3] = "X";
        board[4][4] = "O";
    }

    @Override
    public boolean placePiece(int row, int col, String side) {
        // 若不能下在該位置，直接回傳 false
        if (!canPlace(row, col, side)) return false;

        String opponent = side.equals("X") ? "O" : "X";   // 對手的棋子
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
                {0, 1}, {1, -1}, {1, 0}, {1, 1}};   // 八個方向

        // 檢查每個方向是否可翻轉對手棋子
        for (int[] d : directions) {
            int r = row + d[0], c = col + d[1];
            boolean hasOpponentBetween = false;

            // 找中間有一段對手棋子
            while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c].equals(opponent)) {
                r += d[0];
                c += d[1];
                hasOpponentBetween = true;
            }

            // 如果找到一段對手棋子，且結尾是自己的棋子 → 可以翻轉
            if (hasOpponentBetween && r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c].equals(side)) {
                int rr = row + d[0], cc = col + d[1];
                while (!board[rr][cc].equals(side)) {
                    board[rr][cc] = side;
                    rr += d[0];
                    cc += d[1];
                }
            }
        }

        board[row][col] = side;   // 最後在目標格子放下自己的棋子
        return true;
    }

    // 檢查該位置是否為合法落子點
    public boolean canPlace(int row, int col, String side) {
        if (!board[row][col].equals(".")) return false;   // 格子已被佔用

        String opponent = side.equals("X") ? "O" : "X";
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
                {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        // 每個方向都檢查是否有「被包住」的對手棋子
        for (int[] d : directions) {
            int r = row + d[0], c = col + d[1];
            boolean hasOpponentBetween = false;

            while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c].equals(opponent)) {
                r += d[0];
                c += d[1];
                hasOpponentBetween = true;
            }

            // 中間有對手棋子，且結尾是自己的棋子 → 可以落子
            if (hasOpponentBetween && r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c].equals(side)) {
                return true;
            }
        }

        return false;   // 無法翻轉任何棋子，不能落子
    }

    @Override
    public String getCell(int row, int col) {
        return board[row][col];
    }
    // 取得某格內容

    public String[][] getBoardState() {
        return board;
    }
    // 回傳整個棋盤狀態
}
