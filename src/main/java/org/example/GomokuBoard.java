package org.example;

public class GomokuBoard implements GameBoard {
    private final String[][] board = new String[15][15];

    public GomokuBoard() {
        reset();
    }

    @Override
    public void reset() {
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                board[i][j] = "."; // 這裡一定要是 "." 才能讓 UI 判斷是空格
    }

    @Override
    public boolean placePiece(int row, int col, String side) {
        if (board[row][col].equals(".")) {
            board[row][col] = side;
            return true;
        }
        return false;
    }

    @Override
    public String getCell(int row, int col) {
        return board[row][col];
    }

    public String[][] getBoardState() {
        return board;
    }
}


