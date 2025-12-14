package com.adventofcode.flashk.day09;

import module java.base;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Segment1D {

    private Point up;
    private Point down;
    private Point left;
    private Point right;
    private boolean isVertical;

    public Segment1D(Point first, Point second) {

        if(first.x() == second.x()) {
            // Vertical segment
            up = new Point(first.x(), Math.max(first.y(), second.y()));
            down = new Point(first.x(), Math.min(first.y(), second.y()));
            isVertical = true;
        } else if(first.y() == second.y()) {
            // Horizontal segment
            left = new Point(Math.min(first.x(), second.x()), first.y());
            right = new Point(Math.max(first.x(), second.x()), first.y());
            isVertical = false;
        }

    }

}
