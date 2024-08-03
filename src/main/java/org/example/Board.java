package org.example;

import org.example.strategies.Strategy;

import java.util.Random;

import static java.lang.Math.floorMod;

public class Board {
    private static final char FILLED_PLACE = '█';
    private static final char EMPTY_SPACE = '░';

    private final int width;
    private final int height;
    private boolean[][] grid;

    private Strategy strategy;

    public Board(int width, int height, Strategy strategy) {
        this.width = width;
        this.height = height;
        this.strategy = strategy;
        strategy.setBoard(this);
        emptyGrid();
    }

    private void emptyGrid() {
        grid = new boolean[width][height];
    }


    public void randomize(int alivePercentage) {
        Random rand = new Random();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[x][y] = rand.nextInt(100) < alivePercentage;
            }
        }
    }

    @Deprecated
    public void print() {
        System.out.println();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(grid[x][y] ? FILLED_PLACE : EMPTY_SPACE);
            }
            System.out.println();
        }
    }

    public void fillColumn(int column) {
        if (column >= width || column < 0) {
            throw new IllegalArgumentException("There is not such column");
        }

        for (int y = 0; y < height; y++) {
            grid[column][y] = true;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isAlive(int x, int y) {
        return grid[x][y];
    }

    public void nextGeneration() {
        boolean[][] newGrid = new boolean[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newGrid[x][y] = strategy.calcNextGenerationForCell(x, y);
            }
        }
        grid = newGrid;
    }

    public int liveNeighbourCount(int x, int y) {
        int neighbours = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isAlive(floorMod(x + i, width), floorMod(y + j, height))) {
                    neighbours++;
                }
            }
        }
        if (isAlive(x, y)) {
            neighbours--;
        }
        return neighbours;
    }

}