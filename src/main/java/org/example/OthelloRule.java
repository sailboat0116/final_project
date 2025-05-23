package org.example;

public class OthelloRule implements GameRule {

    @Override
    public boolean checkWin(String[][] board, String side) {
        // 此方法通常用不到直接判斷勝負，而是 UI 根據無合法移動時統一判定勝負。
        return !hasAnyValidMove(board, "X") && !hasAnyValidMove(board, "O");
    }

    public boolean hasAnyValidMove(String[][] board, String side) {
        int SIZE = board.length;
        String opponent = side.equals("X") ? "O" : "X";
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},          {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!board[row][col].equals(".")) continue;

                for (int[] d : directions) {
                    int r = row + d[0], c = col + d[1];
                    boolean hasOpponentBetween = false;

                    while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c].equals(opponent)) {
                        r += d[0];
                        c += d[1];
                        hasOpponentBetween = true;
                    }

                    if (hasOpponentBetween && r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c].equals(side)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public int countPieces(String[][] board, String side) {
        int count = 0;
        for (String[] row : board)
            for (String cell : row)
                if (cell.equals(side)) count++;
        return count;
    }

    public String getWinner(String[][] board) {
        int xCount = countPieces(board, "X");
        int oCount = countPieces(board, "O");
        return (xCount > oCount) ? "X" : (oCount > xCount) ? "O" : "Draw";
    }
}