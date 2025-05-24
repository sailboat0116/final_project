package org.example;

public interface GameFactory {
    //建立一個新的遊戲棋盤。
    GameBoard createBoard();
    //建立一組遊戲規則。
    GameRule createRule();
    //建立一位玩家。
    Player createPlayer(String name, String side);
}

