package com.adventofcode.flashk.day12.generic;

import static java.lang.IO.println;

import module java.base;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Present {

    private static final int PRESENT_SIZE = 3;
    private short index;
    private final List<Shape> shapes;
    private int size;

    public Present(List<String> input) {
        index = Short.parseShort(input.getFirst().replace(":", StringUtils.EMPTY));

        Shape baseShape = new Shape(input);
        shapes = baseShape.getVariations();

        size = input.stream().map(str -> StringUtils.countMatches(str, "#")).mapToInt(Integer::intValue).sum();
    }

}
