package com.adventofcode.flashk.day12.generic;

import static java.lang.IO.println;

import module java.base;
import com.adventofcode.flashk.common.Vector2;
import org.apache.commons.lang3.StringUtils;

public class Region {

    private static final Pattern SIZE_PATTERN = Pattern.compile("(\\d+)x(\\d+):");
    private static final char FILL = '#';
    private static final char EMPTY = '.';

    private short cols; // width
    private short rows; // length
    private final char[][] map;

    private Map<Short, Short> presentsPerIndex = new HashMap<>();
    private Set<Vector2> testPositions = new HashSet<>();

    private int size;

    public Region(String input) {

        // Initialize region dimensions and map
        Matcher sizeMatcher = SIZE_PATTERN.matcher(input);

        if(sizeMatcher.find()) {
            cols = Short.parseShort(sizeMatcher.group(1));
            rows = Short.parseShort(sizeMatcher.group(2));
        }

        map = new char[rows][cols];
        for(int row = 0; row < rows; row++) {
            Arrays.fill(map[row], EMPTY);
        }

        // Initialize expected presents per index
        String[] splittedInput = input.split(":");

        String[] indexes = splittedInput[1].split(StringUtils.SPACE);
        short i = 0;
        for(String index : indexes) {
            if(!StringUtils.EMPTY.equals(index)) {
                presentsPerIndex.put(i++, Short.parseShort(index));
            }
        }

        // Initialize testing positions
        int maxCol = cols - Shape.PRESENT_SIZE + 1;
        int maxRow = rows - Shape.PRESENT_SIZE + 1;

        for(int row = 0; row < maxRow; row++) {
            for(int col = 0; col < maxCol; col++) {
                testPositions.add(new Vector2(col, row));
            }
        }

        // TODO I guess I will need an array that represents the current solution snapshot

        size = cols * rows;
    }

    public boolean canFit(List<Present> presents) {

        // TODO return true if the region can fit all expected presents. false otherwise
        // return false otherwise

        // Process:
        // For every present in the list
        // - get its index
        // - obtain how many presents must be fit at presentsPerIndex
        // - attempt to fit the n presents
        //  > Probably backtracking problem, if no presents can be fit in a branch, they could fit in another position
        //  > If there is no solution in any of the branches that allows to fit the present N times, then stop and return false.
        // - if there is a solution for that present, attempt with the next present.

        // So yes, it is pretty hard
        for(Present present : presents) {

            int goalPresentCount = presentsPerIndex.get(present.getIndex());

            // Skip a presents that do not belong to this region
            if(goalPresentCount == 0 ){
                continue;
            }

            // Attempt to fit N presents
            if(!canFit(present, 0, goalPresentCount)) {
                return false;
            }

        }


        return false;
    }

    private boolean canFit(Present present, int currentPresentCount, int goalPresentCount) {

        if(currentPresentCount == goalPresentCount) {
            return true;
        }

        List<Shape> shapes = present.getShapes();



        // Quizá tenga que añadir un mapa de entrada con el snapshot de la situación
        return false;
    }
}
