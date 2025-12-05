package com.adventofcode.flashk.day05;

import module java.base;
import org.apache.commons.lang3.StringUtils;

public class Cafeteria {

    private final List<Range> ranges = new ArrayList<>();
    private final List<Long> ingredients;

    public Cafeteria(List<String> inputs) {

        // Ranges
        long i = 0;
        for(String input : inputs) {
            i++;
            if(StringUtils.isBlank(input)) {
                break;
            }
            ranges.add(new Range(input));
        }

        // Ingredients
        ingredients = inputs.stream().skip(i).map(Long::parseLong).toList();
    }

    public long solveA() {
        long count = 0;
        for(Long ingredient : ingredients) {
            for(Range range : ranges) {
                if(range.isInRange(ingredient)) {
                    count++;
                    break;
                }
            }
        }

        return count;
    }

    public long solveB() {

        // Take range 1 and reduce ranges 2..N
        // Take range 2 and reduce ranges 3..N
        // Take range N-1 and reduce range N

        for(int i = 0; i < ranges.size(); i++) {
            Range range = ranges.get(i);
            for(int j = i+1; j < ranges.size(); j++) {
                Range otherRange = ranges.get(j);

                // A range might become invalid if is completely overlapped by other, compare only between valid ranges.
                if(otherRange.isValid()) {
                    otherRange.reduce(range);
                }

                if(!range.isValid()) {
                    break;
                }

            }
        }

        return ranges.stream().mapToLong(Range::getIngredientsCount).sum();
    }


}
