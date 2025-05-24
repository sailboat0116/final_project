package org.example;

//GomokuFactory 是用來產生五子棋相關物件的工廠類別，
//實作 GameFactory 介面
public class GomokuFactory implements GameFactory {
    @Override
    public GameBoard createBoard() {
        return new GomokuBoard();
    }
    // 建立並回傳一個新的五子棋棋盤

    @Override
    public GameRule createRule() {
        return new GomokuRule();
    }
    // 建立並回傳五子棋的規則判定物件

    @Override
    public Player createPlayer(String name, String side) {
        return new Player(name, side);
    }
    // 建立並回傳一個玩家物件，設定名稱與陣營（例如 "X" 或 "O")
}


