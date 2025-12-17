package com.adventofcode.flashk.day09.refactor;

import com.adventofcode.flashk.day09.Segment1D;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SweepContext {

    private final TreeSet<Segment1D> activeSegments;
    private Set<Rectangle> activeRectangles = new HashSet<>();

    public SweepContext(boolean isVerticalSweep) {
        if(isVerticalSweep) {
            activeSegments = new TreeSet<>(new ComparatorSegment1DVertical());
        } else {
            activeSegments = new TreeSet<>(new ComparatorSegment1DHorizontal());
        }
    }

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

        validate(rectangle);
        if(rectangle.isValid()) {
            activeRectangles.add(rectangle);
        }

    }

    public void removeRectangle(Rectangle rectangle) {
        this.activeRectangles.remove(rectangle);
    }

    private Rectangle validate(Rectangle rectangle) {

        long validSides = rectangle.getSegments()
                                    .stream()
                                    .filter(this::hasCeiling)
                                    .filter(this::hasFloor)
                                    .count();

        if (validSides != 4) {
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
