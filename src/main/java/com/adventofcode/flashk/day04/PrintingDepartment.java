package com.adventofcode.flashk.day04;

import module java.base;
import com.adventofcode.flashk.common.Vector2;

public class PrintingDepartment {

    private static final char PAPER_ROLL = '@';
    private static final char EMPTY = '.';
    private static final short MAX_ADJACENT_ROLLS = 4;

    private final char[][] map;
    private final int cols;
    private final int rows;

    public PrintingDepartment(char[][] input) {
        map = input;
        cols = map.length;
        rows = map[0].length;
    }

    public long solveA() {

        long count = 0;

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(canMove(row, col)) {
                    count++;
                }
            }
        }

        return count;
    }

    public long solveB() {

        long count = 0;
        boolean hasMoved;

        do {
            hasMoved = false;
            for(int row = 0; row < rows; row++) {
                for(int col = 0; col < cols; col++) {
                    if(canMove(row, col)) {
                        count++;
                        map[row][col] = EMPTY;
                        hasMoved = true;
                    }
                }
            }

        } while(hasMoved);

        return count;
    }

    private boolean canMove(int row, int col) {

        if(map[row][col] != PAPER_ROLL) {
            return false;
        }

        // Generate adjacent positions
        Vector2 position = new Vector2(col, row);
        Set<Vector2> adjacents = getAdjacentTiles(position);

        // Count the number of adjacent paper rolls and verify is below the maximum allowed to forklift
        long count = adjacents.stream().filter(pos -> map[pos.getY()][pos.getX()] == PAPER_ROLL).count();
        return count < MAX_ADJACENT_ROLLS;
    }

    private Set<Vector2> getAdjacentTiles(Vector2 position) {
        Set<Vector2> adjacentTiles = new HashSet<>();

        Vector2 nextPos = Vector2.transform(position, Vector2.left());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(nextPos);
        }

        nextPos = Vector2.transform(position, Vector2.right());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(nextPos);
        }

        nextPos = Vector2.transform(position, Vector2.up());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(nextPos);
        }

        nextPos = Vector2.transform(position, Vector2.down());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(nextPos);
        }

        nextPos = Vector2.transform(position, Vector2.downLeft());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(nextPos);
        }

        nextPos = Vector2.transform(position, Vector2.downRight());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(nextPos);
        }

        nextPos = Vector2.transform(position, Vector2.upLeft());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(nextPos);
        }
        nextPos = Vector2.transform(position, Vector2.upRight());
        if(isInbounds(nextPos)) {
            adjacentTiles.add(nextPos);
        }

        return adjacentTiles;
    }

    private boolean isInbounds(Vector2 pos) {
        return (pos.getY() >= 0 && pos.getY() < rows && pos.getX() >= 0 && pos.getX() < cols);
    }

}
