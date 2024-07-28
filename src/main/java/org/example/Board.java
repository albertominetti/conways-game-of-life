package org.example;

import java.util.Random;

import static java.lang.Math.floorMod;

public class Board {
    private static final char FILLED_PLACE = '█';
    private static final char EMPTY_SPACE = '░';

    private final int width;
    private final int height;
    private boolean[][] grid;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
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
                int liveNeighbours = liveNeighbourCount(x, y);

                if (isAlive(x, y) && liveNeighbours < 2) {
                    // Any live cell with fewer than two live neighbours dies, as if by underpopulation.
                    newGrid[x][y] = false;
                } else if (isAlive(x, y) && (2 <= liveNeighbours && liveNeighbours <= 3)) {
                    // Any live cell with two or three live neighbours lives on to the next generation.
                    newGrid[x][y] = true;
                } else if (isAlive(x, y) && liveNeighbours > 3) {
                    // Any live cell with more than three live neighbours dies, as if by overpopulation.
                    newGrid[x][y] = false;
                } else if (!isAlive(x, y) && liveNeighbours == 3) {
                    // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
                    newGrid[x][y] = true;
                }
            }
        }
        grid = newGrid;
    }

    private int liveNeighbourCount(int x, int y) {
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