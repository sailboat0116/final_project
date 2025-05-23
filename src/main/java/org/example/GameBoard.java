package org.example;

public interface GameBoard {
    void reset();
    boolean placePiece(int row, int col, String side);
    String getCell(int row, int col);
}