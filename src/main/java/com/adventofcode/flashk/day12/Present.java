package com.adventofcode.flashk.day12;

import static java.lang.IO.println;

import module java.base;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Present {

    private static final int PRESENT_SIZE = 3;
    private short index;
    private final List<Shape> shapes;

    public Present(List<String> input) {
        index = Short.parseShort(input.getFirst().replace(":", StringUtils.EMPTY));

        Shape baseShape = new Shape(input);
        shapes = baseShape.getVariations();
        println("ok");
    }

}
