package com.adventofcode.flashk.day09;

import module java.base;

public class MovieTheater {

    private final List<Point> redTiles;
    private final Set<Segment1D> verticalSegments = new HashSet<>();
    private final Set<Segment1D> horizontalSegments = new HashSet<>();

    public MovieTheater(List<String> inputs) {
        redTiles = inputs.stream().map(Point::new).toList();

        // Gen
        //Set<Point> allEdgePoints = new HashSet<>();

        int n = redTiles.size();
        for(int i = 0; i < n; i++) {
            // Take two consecutive points and create a segment
            // The module allows to pick the first one once at the last item
            Point a = redTiles.get(i);
            Point b = redTiles.get((i + 1) % n);
            Segment1D segment = new Segment1D(a,b);

            if(segment.isVertical()) {
                verticalSegments.add(segment);
            } else {
                horizontalSegments.add(segment);
            }
        }
    }

    public long solveA() {
        long result = Long.MIN_VALUE;

        // Buscar en cada par de coordenadas el rectángulo más grande.
        for (int i = 0; i < redTiles.size(); i++) {
            Point firstCorner = redTiles.get(i);

            for (int j = i + 1; j < redTiles.size(); j++) {
                Point secondCorner = redTiles.get(j);
                long area = calculateArea(firstCorner, secondCorner);
                result = Math.max(area, result);
            }
        }

        return result;
    }

    private long calculateArea(Point firstCorner, Point secondCorner) {
        long dx = Math.abs(firstCorner.x() - secondCorner.x()) + 1;
        long dy = Math.abs(firstCorner.y() - secondCorner.y()) + 1;

        return dx * dy;
    }
}
