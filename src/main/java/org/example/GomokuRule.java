package org.example;

public class GomokuRule implements GameRule {
    @Override
    public boolean checkWin(String[][] board, String side) {
        int n = board.length;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (board[i][j].equals(side) &&
                        (checkDirection(board, side, i, j, 1, 0) || // horizontal
                                checkDirection(board, side, i, j, 0, 1) || // vertical
                                checkDirection(board, side, i, j, 1, 1) || // diagonal /
                                checkDirection(board, side, i, j, 1, -1))) // diagonal \
                    return true;

        return false;
    }

    private boolean checkDirection(String[][] board, String side, int r, int c, int dr, int dc) {
        int count = 0;
        for (int k = 0; k < 5; k++) {
            int nr = r + dr * k;
            int nc = c + dc * k;
            if (nr >= 0 && nr < board.length && nc >= 0 && nc < board[0].length && board[nr][nc].equals(side))
                count++;
            else break;
        }
        return count == 5;
    }
}


