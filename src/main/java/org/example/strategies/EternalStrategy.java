package org.example.strategies;

import org.example.Board;

public class EternalStrategy implements Strategy {

    private Board board;

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public boolean calcNextGenerationForCell(int x, int y) {
        return board.isAlive(x, y);
    }

    @Override
    public String name() {
        return "Eternal";
    }
}