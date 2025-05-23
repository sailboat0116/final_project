package org.example;

public class GomokuFactory implements GameFactory {
    @Override
    public GameBoard createBoard() {
        return new GomokuBoard();
    }

    @Override
    public GameRule createRule() {
        return new GomokuRule();
    }

    @Override
    public Player createPlayer(String name, String side) {
        return new Player(name, side);
    }
}


