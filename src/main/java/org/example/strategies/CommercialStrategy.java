package org.example.strategies;

import org.example.Board;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.floorMod;

public class CommercialStrategy implements Strategy {

    private Board board;

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public boolean calcNextGenerationForCell(int x, int y) {
        boolean isCurrentCellAlive = board.isAlive(x, y);

        int liveNeighbours = board.liveNeighbourCount(x, y);
        int liveEdgeNeighbours = liveEdgeNeighbourCount(x, y);


        if (isCurrentCellAlive) {
            if (2 <= liveNeighbours && liveNeighbours <= 3) {
                isCurrentCellAlive = true;
            } else if (liveEdgeNeighbours >= 2 && liveNeighbours == liveEdgeNeighbours) {
                isCurrentCellAlive = true;
            } else {
                isCurrentCellAlive = false;
            }
        }

        if (!isCurrentCellAlive) {
            if (liveNeighbours == 3) {
                isCurrentCellAlive = true;

            }
            if ((liveNeighbours == 4 || liveNeighbours == 5) && liveEdgeNeighbours == 4) {
                isCurrentCellAlive = true;
            }
        }

        return isCurrentCellAlive;
    }


    private int liveEdgeNeighbourCount(int x, int y) {
        List<Boolean> edges = Arrays.asList(
                board.isAlive(floorMod(x - 1, board.getWidth()), floorMod(y - 1, board.getHeight())),
                board.isAlive(floorMod(x + 1, board.getWidth()), floorMod(y - 1, board.getHeight())),
                board.isAlive(floorMod(x + 1, board.getWidth()), floorMod(y + 1, board.getHeight())),
                board.isAlive(floorMod(x - 1, board.getWidth()), floorMod(y + 1, board.getHeight()))
        );
        return (int) edges.stream().filter(b -> b).count();
    }

    @Override
    public String name() {
        return "Commercial";
    }

}
