package org.example.strategies;

import org.example.Board;

public class GnarlStrategy implements Strategy {

    private Board board;

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public boolean calcNextGenerationForCell(int x, int y) {
        boolean isCurrentCellAlive = board.isAlive(x, y);

        int liveNeighbours = board.liveNeighbourCount(x, y);

        if (isCurrentCellAlive && liveNeighbours != 1) {
            isCurrentCellAlive = false;
        } else if (isCurrentCellAlive && liveNeighbours == 1) {
            isCurrentCellAlive = true;
        } else if (!isCurrentCellAlive && liveNeighbours == 1) {
            isCurrentCellAlive = true;
        } else if (!isCurrentCellAlive) {
            isCurrentCellAlive = false;
        }

        return isCurrentCellAlive;
    }

    @Override
    public String name() {
        return "Gnarl";
    }

}
