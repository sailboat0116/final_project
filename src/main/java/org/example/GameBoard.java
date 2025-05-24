package org.example;
//GameBoard 是一個棋盤介面，用來定義遊戲棋盤應具備的基本操作。
public interface GameBoard {
    void reset();
    //重設棋盤狀態，將所有格子清空或設為初始狀態。
    boolean placePiece(int row, int col, String side);
    //取得指定格子上的內容。
    String getCell(int row, int col);
}