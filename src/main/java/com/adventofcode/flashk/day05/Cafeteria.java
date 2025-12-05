package com.adventofcode.flashk.day05;

import static java.lang.IO.println;

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
        ingredients = inputs.stream()
                .skip(i)
                .map(Long::parseLong)
                .toList();
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


        for(int i = 0; i < ranges.size(); i++) {
            Range range = ranges.get(i);
            for(int j = i+1; j < ranges.size(); j++) {
                Range otherRange = ranges.get(j);

                if(otherRange.isValid()) {
                    otherRange.reduce(range);
                }

                if(!range.isValid()) {
                    break;
                }

            }
        }

        // Coge rango 1 y resta a rangos 2..N
        // Coge rango 2 y resta a rangos 3..N
        // Coge rango N-1 y resta a rango N
        // Ahora en cada rango solo estarán los números sin repetir

        return ranges.stream().mapToLong(Range::getIngredientsCount).sum();
    }


}
