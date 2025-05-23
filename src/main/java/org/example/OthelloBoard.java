package org.example;

public class OthelloBoard implements GameBoard {
    private final int SIZE = 8;
    private final String[][] board = new String[SIZE][SIZE];

    public OthelloBoard() {
        reset();
    }

    @Override
    public void reset() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = ".";

        board[3][3] = "O";
        board[3][4] = "X";
        board[4][3] = "X";
        board[4][4] = "O";
    }

    @Override
    public boolean placePiece(int row, int col, String side) {
        if (!board[row][col].equals(".")) return false;

        boolean validMove = false;
        String opponent = side.equals("X") ? "O" : "X";
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
                {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int[] d : directions) {
            int r = row + d[0], c = col + d[1];
            boolean hasOpponentBetween = false;

            while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c].equals(opponent)) {
                r += d[0];
                c += d[1];
                hasOpponentBetween = true;
            }

            if (hasOpponentBetween && r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c].equals(side)) {
                // 有效走法：翻轉
                int rr = row + d[0], cc = col + d[1];
                while (!board[rr][cc].equals(side)) {
                    board[rr][cc] = side;
                    rr += d[0];
                    cc += d[1];
                }
                validMove = true;
            }
        }

        if (validMove) board[row][col] = side;
        return validMove;
    }

    @Override
    public String getCell(int row, int col) {
        return board[row][col];
    }

    public String[][] getBoardState() {
        return board;
    }
}

