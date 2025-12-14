package com.adventofcode.flashk.day09;

import module java.base;

public record Point(int x, int y) {

    public Point(String coordinates) {
        String[] values = coordinates.split(",");

        int x = Integer.parseInt(values[0]);
        int y = Integer.parseInt(values[1]);

        this(x, y);
    }
}
