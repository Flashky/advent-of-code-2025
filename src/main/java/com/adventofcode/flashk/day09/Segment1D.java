package com.adventofcode.flashk.day09;

import module java.base;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode
public class Segment1D implements Comparable<Segment1D> {

    private final Point first;
    private final Point second;
    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;
    private final boolean isVertical;

    public Segment1D(Point a, Point b) {
        first = a;
        second = b;
        minX = Math.min(first.x(), second.x());
        maxX = Math.max(first.x(), second.x());
        minY = Math.min(first.y(), second.y());
        maxY = Math.max(first.y(), second.y());
        isVertical = first.x() == second.x();
    }

    @Override
    public int compareTo(@NotNull Segment1D other) {

        // Order first by lowest 'x' coordinate
        if(minX != other.minX) {
            return Integer.compare(minX, other.minX);
        }

        // Order by lowest 'y' coordinate
        return Integer.compare(minY, other.minY);
    }
}
