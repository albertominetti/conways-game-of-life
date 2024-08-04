package org.example;

import org.example.strategies.Strategy;
import org.example.strategies.byset.StandardBySetStrategy;

import java.util.Random;

import static java.lang.Math.floorMod;

public class Board {
    private static final char FILLED_PLACE = '█';
    private static final char EMPTY_SPACE = '░';

    private final int width;
    private final int height;
    private boolean[][] grid;
    private long generation;
    private Strategy strategy;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        setStrategy(new StandardBySetStrategy());
        grid = new boolean[this.width][this.height];
    }


    public void randomize(int alivePercentage) {
        Random rand = new Random();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[x][y] = rand.nextInt(100) < alivePercentage;
            }
        }
        generation = 0;
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
        generation++;
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

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
        strategy.setBoard(this);
    }

    public void setCell(int x, int y, boolean alive) {
        grid[x][y] = alive;
    }

    public boolean[][] getGridSnapshot() {
        boolean[][] snapshotGrid = new boolean[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            boolean[] aMatrix = grid[i];
            int aLength = aMatrix.length;
            snapshotGrid[i] = new boolean[aLength];
            System.arraycopy(aMatrix, 0, snapshotGrid[i], 0, aLength);
        }
        return snapshotGrid;
    }

    public long getGeneration() {
        return generation;
    }
}