package org.example.strategies;

import org.example.Board;

public interface Strategy {
    void setBoard(Board board);
    boolean calcNextGenerationForCell(int x, int y);
}
