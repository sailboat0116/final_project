package org.example;

//GomokuRule 實作 GameRule，用來判斷五子棋是否有玩家獲勝
public class GomokuRule implements GameRule {
    @Override
    public boolean checkWin(String[][] board, String side) {
        int n = board.length; // 棋盤大小（預設為 15）

        //掃描整個棋盤
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                // 如果該格是當前玩家的棋子，檢查四個方向是否連成五子
                if (board[i][j].equals(side) &&
                        (checkDirection(board, side, i, j, 1, 0) || // horizontal
                                checkDirection(board, side, i, j, 0, 1) || // vertical
                                checkDirection(board, side, i, j, 1, 1) || // diagonal /
                                checkDirection(board, side, i, j, 1, -1))) // diagonal \
                    return true;   // 有任一方向成立就代表該玩家獲勝

        return false;   // 掃完棋盤後仍無五子連線，則尚未勝利
    }

    //檢查某一方向上是否連續 5 顆棋子是同一方的
    private boolean checkDirection(String[][] board, String side, int r, int c, int dr, int dc) {
        int count = 0;
        for (int k = 0; k < 5; k++) {
            int nr = r + dr * k;
            int nc = c + dc * k;
            // 確保不超出邊界且格子符合玩家方
            if (nr >= 0 && nr < board.length && nc >= 0 && nc < board[0].length && board[nr][nc].equals(side))
                count++;
            else break;   // 一旦中斷就停止計數
        }
        return count == 5;   // 只有剛好連續 5 顆才算勝利
    }
}


