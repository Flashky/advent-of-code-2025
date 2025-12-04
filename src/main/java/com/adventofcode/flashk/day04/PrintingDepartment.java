package com.adventofcode.flashk.day04;

import module java.base;
import com.adventofcode.flashk.common.Vector2;

public class PrintingDepartment {

    private static final char PAPER_ROLL = '@';
    private static final char EMPTY = '.';
    private static final short MAX_ADJACENT_ROLLS = 4;
    private final Set<Vector2> directions = Set.of(Vector2.right(), Vector2.left(),
                                                    Vector2.up(), Vector2.down(),
                                                    Vector2.upLeft(), Vector2.upRight(),
                                                    Vector2.downLeft(), Vector2.downRight());

    private final char[][] map;
    private final int cols;
    private final int rows;

    public PrintingDepartment(char[][] input) {
        map = input;
        cols = map.length;
        rows = map[0].length;
    }

    public long solve(boolean removeRolls) {

        long count = 0;
        boolean hasMoved;

        do {
            hasMoved = false;
            for(int row = 0; row < rows; row++) {
                for(int col = 0; col < cols; col++) {
                    if(canMove(row, col)) {
                        count++;
                        if(removeRolls) {
                            map[row][col] = EMPTY;
                            hasMoved = true;
                        }
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

        for(Vector2 direction : directions) {
            Vector2 nextPos = Vector2.transform(position, direction);
            if(isInbounds(nextPos)){
                adjacentTiles.add(nextPos);
            }
        }

        return adjacentTiles;
    }

    private boolean isInbounds(Vector2 pos) {
        return (pos.getY() >= 0 && pos.getY() < rows && pos.getX() >= 0 && pos.getX() < cols);
    }

}
