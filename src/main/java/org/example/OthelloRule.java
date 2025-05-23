package org.example;

public class OthelloRule implements GameRule {
    @Override
    public boolean checkWin(String[][] board, String side) {
        boolean hasMove = false;
        int size = board.length;
        String opponent = side.equals("X") ? "O" : "X";

        outer: for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col].equals(".")) {
                    hasMove = true;
                    break outer;
                }
            }
        }

        return !hasMove;
    }
}

