package com.adventofcode.flashk.day09.refactor;

import com.adventofcode.flashk.day09.Point;
import com.adventofcode.flashk.day09.Segment1D;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class Rectangle {

    private final long area;
    private final Set<Segment1D> verticalSegments = new HashSet<>();
    private final Set<Segment1D> horizontalSegments = new HashSet<>();

    @Setter
    private boolean isValid = true;

    public Rectangle(Point a, Point b) {

        area = area(a,b);

        int minX = Math.min(a.x(), b.x());
        int maxX = Math.max(a.x(), b.x());
        int minY = Math.min(a.y(), b.y());
        int maxY = Math.max(a.y(), b.y());

        Point p1 =  new Point(minX, minY);
        Point p2 =  new Point(minX, maxY);
        Point p3 =  new Point(maxX, minY);
        Point p4 =  new Point(maxX, maxY);

        verticalSegments.add(new Segment1D(p1, p2));
        verticalSegments.add(new Segment1D(p3, p4));
        horizontalSegments.add(new Segment1D(p1, p3));
        horizontalSegments.add(new Segment1D(p2, p4));
    }

    private long area(Point a, Point b) {
        long dx = Math.abs(a.x() - b.x()) + 1L;
        long dy = Math.abs(a.y() - b.y()) + 1L;

        return dx * dy;
    }
}
