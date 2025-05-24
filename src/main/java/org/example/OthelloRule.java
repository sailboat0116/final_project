package org.example;

//OthelloRule 實作 GameRule，定義黑白棋的規則判定邏輯。
public class OthelloRule implements GameRule {

    @Override
    public boolean checkWin(String[][] board, String side) {
        // 此方法通常用不到直接判斷勝負，而是 UI 根據無合法移動時統一判定勝負。
        return !hasAnyValidMove(board, "X") && !hasAnyValidMove(board, "O");
    }

    //檢查指定玩家是否還有合法落子位置
    public boolean hasAnyValidMove(String[][] board, String side) {
        int SIZE = board.length;
        String opponent = side.equals("X") ? "O" : "X";
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},          {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };   // 八個方向

        // 掃描整個棋盤，檢查是否有能翻轉對手棋子的合法落點
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!board[row][col].equals(".")) continue;   // 該格已有棋子

                for (int[] d : directions) {
                    int r = row + d[0], c = col + d[1];
                    boolean hasOpponentBetween = false;

                    // 往該方向前進，找一段對手棋子
                    while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c].equals(opponent)) {
                        r += d[0];
                        c += d[1];
                        hasOpponentBetween = true;
                    }

                    // 若有對手棋子且終點是自己棋子 → 合法
                    if (hasOpponentBetween && r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c].equals(side)) {
                        return true;
                    }
                }
            }
        }

        return false;   // 全盤無合法行動
    }

    //計算棋盤上某方棋子總數
    public int countPieces(String[][] board, String side) {
        int count = 0;
        for (String[] row : board)
            for (String cell : row)
                if (cell.equals(side)) count++;
        return count;
    }

    //判斷勝利方
    public String getWinner(String[][] board) {
        int xCount = countPieces(board, "X");
        int oCount = countPieces(board, "O");
        return (xCount > oCount) ? "X" : (oCount > xCount) ? "O" : "Draw";
    }
}