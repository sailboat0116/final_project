package org.example;

public class OthelloFactory implements GameFactory {
    @Override
    public GameBoard createBoard() {
        return new OthelloBoard();
    }

    @Override
    public GameRule createRule() {
        return new OthelloRule();
    }

    @Override
    public Player createPlayer(String name, String side) {
        return new Player(name, side);
    }
}