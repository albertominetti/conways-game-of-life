package org.example.strategies;

import org.example.Board;

public class WalledCitiesStrategy implements Strategy {

    private Board board;

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public boolean calcNextGenerationForCell(int x, int y) {
        boolean isCurrentCellAlive = board.isAlive(x, y);

        int liveNeighbours = board.liveNeighbourCount(x, y);

        if (isCurrentCellAlive && liveNeighbours < 2) {
            isCurrentCellAlive = false;
        } else if (isCurrentCellAlive && (2 <= liveNeighbours && liveNeighbours <= 5)) {
            isCurrentCellAlive = true;
        } else if (isCurrentCellAlive && liveNeighbours > 5) {
            isCurrentCellAlive = false;
        } else if (!isCurrentCellAlive && (4 <= liveNeighbours && liveNeighbours <= 8)) {
            isCurrentCellAlive = true;
        }
        return isCurrentCellAlive;
    }

    @Override
    public String name() {
        return "Walled cities";
    }

}
