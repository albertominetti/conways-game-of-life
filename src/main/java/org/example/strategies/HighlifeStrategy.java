package org.example.strategies;

import org.example.Board;

@Deprecated // in favour of HighLifeBySetStrategy
public class HighlifeStrategy implements Strategy {

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
            // Any live cell with fewer than two live neighbours dies, as if by underpopulation.
            isCurrentCellAlive = false;
        } else if (isCurrentCellAlive && (2 <= liveNeighbours && liveNeighbours <= 3)) {
            // Any live cell with two or three live neighbours lives on to the next generation.
            isCurrentCellAlive = true;
        } else if (isCurrentCellAlive && liveNeighbours > 3) {
            // Any live cell with more than three live neighbours dies, as if by overpopulation.
            isCurrentCellAlive = false;
        } else if (!isCurrentCellAlive && (liveNeighbours == 3 || liveNeighbours == 6)) {
            // Any dead cell with exactly three or six live neighbours becomes a live cell, as if by reproduction.
            isCurrentCellAlive = true;
        }
        return isCurrentCellAlive;
    }

}
