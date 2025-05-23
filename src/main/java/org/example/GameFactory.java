package org.example;

public interface GameFactory {
    GameBoard createBoard();
    GameRule createRule();
    Player createPlayer(String name, String side);
}

