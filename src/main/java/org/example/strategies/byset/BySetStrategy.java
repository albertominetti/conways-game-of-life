package org.example.strategies.byset;

import org.example.Board;
import org.example.strategies.Strategy;

import java.util.Collection;
import java.util.Collections;

public abstract class BySetStrategy implements Strategy {

    private Board board;

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public boolean calcNextGenerationForCell(int x, int y) {
        boolean isCurrentCellAlive = board.isAlive(x, y);
        int liveNeighbours = board.liveNeighbourCount(x, y);

        if (isCurrentCellAlive) {
            return whenAliveKeepAlive().contains(liveNeighbours);
        } else {
            return whenDeadResurrect().contains(liveNeighbours);
        }
    }

    protected abstract Collection<Integer> whenAliveKeepAlive();

    protected abstract Collection<Integer> whenDeadResurrect();

}