package com.adventofcode.flashk.day09;

import static java.lang.IO.println;

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

    public long solveB() {
        long result = Long.MIN_VALUE;

        // Buscar en cada par de coordenadas el rectángulo más grande.
        for(int i = 0; i < redTiles.size(); i++) {
            Point firstCorner = redTiles.get(i);

            for(int j = i + 1; j < redTiles.size(); j++) {
                Point secondCorner = redTiles.get(j);
                long area = calculateArea(firstCorner, secondCorner);

                if(area < result) {
                    continue;
                }

                if(isValidRectangle(firstCorner, secondCorner)) {
                    result = area;
                }
            }
        }

        return result;
    }

    private boolean isValidRectangle(Point firstCorner, Point secondCorner) {
        // Make both a vertical and a valid sweep
        return isValidSweepVertical(firstCorner, secondCorner) && isValidSweepHorizontal(firstCorner, secondCorner);
    }

    private boolean isValidSweepVertical(Point firstCorner, Point secondCorner) {

        // Check horizontal segments
        PriorityQueue<Event1D> events = prepareVerticalEvents(firstCorner, secondCorner);

        // Will allow to check if there are segments over and below the square edges
        TreeSet<Integer> yCoordinates = new TreeSet<>();

        // Variables to handle square edge detection
        int squareLowerY = -1;
        int squareHigherY = -1;

        while(!events.isEmpty()) {

            Event1D currentEvent = events.poll();

            switch(currentEvent.getType()) {
                case START:
                    yCoordinates.add(currentEvent.getSegment().getLeft().y());
                    break;
                case END:
                    yCoordinates.remove(currentEvent.getSegment().getLeft().y());

                    // Check for upper square segment
                    if (hasNoCeiling(yCoordinates, squareHigherY)) {
                        return false;
                    }

                    // Check for lower square segment
                    if (hasNoFloor(yCoordinates, squareLowerY)) {
                        return false;
                    }

                    break;
                case START_SQ_HIGHER:
                    squareHigherY = currentEvent.getSegment().getLeft().y();
                    if (hasNoCeiling(yCoordinates, squareHigherY)) {
                        return false;
                    }

                    break;
                case START_SQ_LOWER:
                    squareLowerY = currentEvent.getSegment().getLeft().y();
                    if (hasNoFloor(yCoordinates, squareLowerY)) {
                        return false;
                    }
                    break;
                case END_SQ_HIGHER:
                case END_SQ_LOWER:
                    return true;
            }

        }

        return true;

    }

    private static boolean hasNoFloor(TreeSet<Integer> yCoordinates, int squareLowerY) {
        // Calculation does not apply if there is no lower square edge
        if(squareLowerY == -1) {
            return false;
        }

        return yCoordinates.floor(squareLowerY) == null;
    }

    private static boolean hasNoCeiling(TreeSet<Integer> yCoordinates, int squareHigherY) {
        // Calculation does not apply if there is no higher square edge
        if(squareHigherY == -1){
            return false;
        }
        return yCoordinates.ceiling(squareHigherY) == null;
    }

    private PriorityQueue<Event1D> prepareVerticalEvents(Point firstCorner, Point secondCorner) {

        PriorityQueue<Event1D> events = new PriorityQueue<>();

        // There are no horizontal events if the rectangle is just a vertical line
        if(firstCorner.x() == secondCorner.x()) {
            return events;
        }

        // Rectangle horizontal segments events
        int minX = Math.min(firstCorner.x(), secondCorner.x());
        int maxX = Math.max(firstCorner.x(), secondCorner.x());
        int minY = Math.min(firstCorner.y(), secondCorner.y());
        int maxY = Math.max(firstCorner.y(), secondCorner.y());

        // Upper segment
        Segment1D higherSegment = new Segment1D(new Point(minX,maxY), new Point(maxX, maxY));
        events.add(new Event1D(higherSegment.getLeft().x(), higherSegment, EventType1D.START_SQ_HIGHER));
        events.add(new Event1D(higherSegment.getRight().x(), higherSegment, EventType1D.END_SQ_HIGHER));

        // Lower segment
        Segment1D lowerSegment = new Segment1D(new Point(minX,minY), new Point(maxX, minY));
        if(!higherSegment.equals(lowerSegment)) {
            events.add(new Event1D(lowerSegment.getLeft().x(), lowerSegment, EventType1D.START_SQ_LOWER));
            events.add(new Event1D(lowerSegment.getRight().x(), lowerSegment, EventType1D.END_SQ_LOWER));
        }

        // Polygon segments
        for(Segment1D segment : horizontalSegments) {
            events.add(new Event1D(segment.getLeft().x(), segment, EventType1D.START));
            events.add(new Event1D(segment.getRight().x(), segment, EventType1D.END));
        }

        return events;
    }

    private boolean isValidSweepHorizontal(Point firstCorner, Point secondCorner) {

        // Check horizontal segments
        PriorityQueue<Event1D> events = prepareHorizontalEvents(firstCorner, secondCorner);

        // Will allow to check if there are segments left and right the square edges
        TreeSet<Integer> xCoordinates = new TreeSet<>();

        // Variables to handle square edge detection
        int squareLowerX = -1;
        int squareHigherX = -1;

        while(!events.isEmpty()) {

            Event1D currentEvent = events.poll();

            switch(currentEvent.getType()) {
                case START:
                    xCoordinates.add(currentEvent.getSegment().getUp().x());
                    break;
                case END:
                    xCoordinates.remove(currentEvent.getSegment().getUp().x());

                    // Check for upper square segment
                    if (hasNoCeiling(xCoordinates, squareHigherX)) {
                        return false;
                    }

                    // Check for lower square segment
                    if (hasNoFloor(xCoordinates, squareLowerX)) {
                        return false;
                    }

                    break;
                case START_SQ_HIGHER:
                    squareHigherX = currentEvent.getSegment().getUp().x();
                    if (hasNoCeiling(xCoordinates, squareHigherX)) {
                        return false;
                    }

                    break;
                case START_SQ_LOWER:
                    squareLowerX = currentEvent.getSegment().getUp().x();
                    if (hasNoFloor(xCoordinates, squareLowerX)) {
                        return false;
                    }
                    break;
                case END_SQ_HIGHER:
                case END_SQ_LOWER:
                    return true;
            }

        }

        return true;
    }

    private PriorityQueue<Event1D> prepareHorizontalEvents(Point firstCorner, Point secondCorner) {

        PriorityQueue<Event1D> events = new PriorityQueue<>();

        // There are no horizontal events if the rectangle is just a horizontal line
        if(firstCorner.y() == secondCorner.y()) {
            return events;
        }

        // Rectangle horizontal segments events
        int minX = Math.min(firstCorner.x(), secondCorner.x());
        int maxX = Math.max(firstCorner.x(), secondCorner.x());
        int minY = Math.min(firstCorner.y(), secondCorner.y());
        int maxY = Math.max(firstCorner.y(), secondCorner.y());

        // Upper segment (right)
        Segment1D rightSegment = new Segment1D(new Point(maxX,minY), new Point(maxX, maxY));
        events.add(new Event1D(rightSegment.getDown().y(), rightSegment, EventType1D.START_SQ_HIGHER));
        events.add(new Event1D(rightSegment.getUp().y(), rightSegment, EventType1D.END_SQ_HIGHER));

        // Lower segment (left)
        Segment1D leftSegment = new Segment1D(new Point(minX,minY), new Point(minX, maxY));
        if(!rightSegment.equals(leftSegment)) {
            events.add(new Event1D(leftSegment.getDown().y(), leftSegment, EventType1D.START_SQ_LOWER));
            events.add(new Event1D(leftSegment.getUp().y(), leftSegment, EventType1D.END_SQ_LOWER));
        }

        // Polygon segments
        for(Segment1D segment : verticalSegments) {
            events.add(new Event1D(segment.getDown().y(), segment, EventType1D.START));
            events.add(new Event1D(segment.getUp().y(), segment, EventType1D.END));
        }

        return events;
    }

    private long calculateArea(Point firstCorner, Point secondCorner) {
        long dx = Math.abs(firstCorner.x() - secondCorner.x()) + 1;
        long dy = Math.abs(firstCorner.y() - secondCorner.y()) + 1;

        return dx * dy;
    }
}
