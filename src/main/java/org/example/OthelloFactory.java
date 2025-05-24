package org.example;

//OthelloFactory 是黑白棋（Othello）的工廠類別
//負責建立與此遊戲相關的物件（棋盤、規則、玩家）
public class OthelloFactory implements GameFactory {
    @Override
    public GameBoard createBoard() {
        return new OthelloBoard();
    }
    // 建立並回傳黑白棋專用的棋盤物件

    @Override
    public GameRule createRule() {
        return new OthelloRule();
    }
    // 建立並回傳黑白棋專用的規則判定物件

    @Override
    public Player createPlayer(String name, String side) {
        return new Player(name, side);
    }
    // 建立並回傳一位玩家，指定名字與陣營（X 或 O）
}