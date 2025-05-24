package org.example;

//GomokuBoard 是五子棋的棋盤實作，實作自 GameBoard 介面
public class GomokuBoard implements GameBoard {
    private final String[][] board = new String[15][15];

    public GomokuBoard() {
        reset();
    }
    // 建構時初始化棋盤

    @Override
    public void reset() {
        // 將所有格子設為 "."，表示空格
        // （UI 會依據 "." 判斷這是未下子的格子）
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                board[i][j] = "."; // 這裡一定要是 "." 才能讓 UI 判斷是空格
    }

    @Override
    public boolean placePiece(int row, int col, String side) {
        // 若該格是空的，放置棋子並回傳 true，否則回傳 false
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
    // 取得指定格子的內容（"."、"X"、"O"...）

    public String[][] getBoardState() {
        return board;
    }
    // 回傳整個棋盤的目前狀態
}


