package com.adventofcode.flashk.day09.refactor;

import com.adventofcode.flashk.day09.Segment1D;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SweepContextY {

    private final TreeSet<Segment1D> activeSegments = new TreeSet<>();
    private Set<Rectangle> activeRectangles = new HashSet<>();

    public void addSegment(Segment1D segment) {
        activeSegments.add(segment);
    }

    public void removeSegment(Segment1D segment) {
        activeSegments.remove(segment);
        activeRectangles = activeRectangles.stream()
                                            .map(this::validate)
                                            .filter(Rectangle::isValid)
                                            .collect(Collectors.toSet());
    }

    public void addRectangle(Rectangle rectangle) {

        // TODO y si el rectángulo ya se había borrado del contexto?

        validate(rectangle);
        if(rectangle.isValid()) {
            activeRectangles.add(rectangle);
        }
    }

    public void removeRectangle(Rectangle rectangle) {
        this.activeRectangles.remove(rectangle);
    }

    private Rectangle validate(Rectangle rectangle) {

        long validSides = rectangle.getVerticalSegments().stream()
                                    .filter(this::hasCeiling)
                                    .filter(this::hasFloor)
                                    .count();
/*
        long validSides += rectangle.getHorizontalSegments().stream()
                                    .filter(this::hasCeiling)
                                    .filter(this::hasFloor)
                                    .count();
*/
        if (validSides != 2) {
            rectangle.setValid(false);
        }

        return rectangle;

    }

    private boolean hasCeiling(Segment1D segment) {
        return activeSegments.ceiling(segment) != null;
    }

    private boolean hasFloor(Segment1D segment) {
        return activeSegments.floor(segment) != null;
    }

}
