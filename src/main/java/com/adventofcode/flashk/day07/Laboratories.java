package com.adventofcode.flashk.day07;

import module java.base;
import com.adventofcode.flashk.common.Vector2;

import static java.util.function.Predicate.not;

public class Laboratories {

    private static final char START = 'S';
    private static final char SPLITTER = '^';
    private static final char BEAM = '|';
    private static final char EMPTY = '.';
    private static final Vector2 DOWN = Vector2.up();
    private static final Vector2 LEFT = Vector2.left();
    private static final Vector2 RIGHT = Vector2.right();

    private final char[][] map;
    private final int rows;
    private final int cols;
    private Vector2 start;

    private final Set<Vector2> splitterEntries = new HashSet<>();

    public Laboratories(char[][] inputs) {
        map = inputs;
        rows = map.length;
        cols = map[0].length;

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(map[row][col] == START) {
                    start = new Vector2(col,0);
                } else if(map[row][col] == SPLITTER) {
                    splitterEntries.add(new Vector2(col, row-1));
                }
            }
        }

    }

    public long solveA() {
        bfs();
        return splitterEntries.stream().filter(not(this::isEmpty)).count();

    }

    public long solveB() {
        return dfs(start, new HashMap<>());
    }

    private void bfs() {
        Queue<Vector2> beams = new ArrayDeque<>();
        beams.add(start);

        while(!beams.isEmpty()) {
            Vector2 pos = beams.poll();

            Set<Vector2> adjacents = getAdjacents(pos);
            for(Vector2 adjacentPos : adjacents) {
                if(isInbounds(adjacentPos) && isEmpty(adjacentPos)) {
                    map[adjacentPos.getY()][adjacentPos.getX()] = BEAM;
                    beams.add(adjacentPos);
                }
            }
        }
    }

    private long dfs(Vector2 pos, Map<Vector2,Long> memo) {

        if(!isInbounds(pos)) {
            return 1;
        } else if(memo.containsKey(pos)) {
            return memo.get(pos);
        }

        long result = 0;
        Set<Vector2> adjacents = getAdjacents(pos);
        for(Vector2 adjacentPos : adjacents) {
            result += dfs(adjacentPos, memo);
        }

        memo.put(pos, result);

        return result;
    }


    private Set<Vector2> getAdjacents(Vector2 pos) {

        Set<Vector2> adjacentTiles = new HashSet<>();

        Vector2 nextPos = Vector2.transform(pos, DOWN);

        if(isInbounds(nextPos) && isSplitter(nextPos)) {
            // Splitter found
            adjacentTiles.add(Vector2.transform(nextPos, LEFT));
            adjacentTiles.add(Vector2.transform(nextPos, RIGHT));

        } else {
            // Beam continues down
            adjacentTiles.add(nextPos);
        }

        return adjacentTiles;
    }

    private boolean isSplitter(Vector2 pos) {
        return map[pos.getY()][pos.getX()] == SPLITTER;
    }

    private boolean isEmpty(Vector2 pos) {
        return map[pos.getY()][pos.getX()] == EMPTY;
    }

    private boolean isInbounds(Vector2 pos) {
        return (pos.getY() >= 0 && pos.getY() < rows && pos.getX() >= 0 && pos.getX() < cols);
    }

}
