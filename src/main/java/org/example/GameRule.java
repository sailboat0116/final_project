package org.example;
//GameRule 是用來定義遊戲勝利條件的介面
public interface GameRule {
    //檢查指定玩家是否已經獲勝
    boolean checkWin(String[][] board, String side);
}
