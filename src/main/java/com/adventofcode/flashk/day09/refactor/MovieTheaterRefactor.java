package com.adventofcode.flashk.day09.refactor;


import module java.base;
import com.adventofcode.flashk.day09.EventType1D;
import com.adventofcode.flashk.day09.Point;
import com.adventofcode.flashk.day09.Segment1D;

public class MovieTheaterRefactor {

    private final List<Point> redTiles;
    private final Set<Rectangle> rectangles = new HashSet<>();
    private final Set<Segment1D> verticalSegments = new HashSet<>();
    private final Set<Segment1D> horizontalSegments = new HashSet<>();

    public MovieTheaterRefactor(List<String> inputs) {
        redTiles = inputs.stream().map(Point::new).toList();

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

        // Generate rectangles
        for(int i = 0; i < redTiles.size(); i++) {
            Point a = redTiles.get(i);
            for(int j = i+1; j < redTiles.size(); j++) {
                Point b = redTiles.get(j);
                rectangles.add(new Rectangle(a,b));
            }
        }

        sweep();

        return rectangles.stream().filter(Rectangle::isValid).mapToLong(Rectangle::getArea).max().getAsLong();
    }

    private void sweep() {
        sweep(true);
        sweep(false);
    }

    private void sweep(boolean isVerticalSweep) {

        PriorityQueue<Event1DRefactor> events = prepareEvents(isVerticalSweep);
        SweepContext context = new SweepContext(isVerticalSweep);

        while(!events.isEmpty()) {

            Event1DRefactor currentEvent = events.poll();

            switch(currentEvent.getType()) {
                case START:
                    context.addSegment(currentEvent.getSegment());
                    break;
                case END:
                    context.removeSegment(currentEvent.getSegment());
                    break;
                case START_RECTANGLE:
                    context.addRectangle(currentEvent.getRectangle());
                    break;
                case END_RECTANGLE:
                    context.removeRectangle(currentEvent.getRectangle());
                    break;

            }

        }
    }

    private PriorityQueue<Event1DRefactor> prepareEvents(boolean isVerticalSweep) {
        return isVerticalSweep ? prepareVerticalEvents() : prepareHorizontalEvents();
    }

    private PriorityQueue<Event1DRefactor> prepareVerticalEvents() {

        PriorityQueue<Event1DRefactor> events = new PriorityQueue<>();

        // There are no vertical events if the rectangle is just a vertical line

        // Add all rectangle segments
        for(Rectangle rectangle : rectangles) {
            if(rectangle.isValid()) {
                Set<Segment1D> rectangleSegments = rectangle.getHorizontalSegments();
                for (Segment1D segment : rectangleSegments) {
                    events.add(new Event1DRefactor(segment.getMinX(), rectangle, EventType1D.START_RECTANGLE));
                    events.add(new Event1DRefactor(segment.getMaxX(), rectangle, EventType1D.END_RECTANGLE));
                }
            }
        }

        // Polygon segments
        for(Segment1D segment : horizontalSegments) {
            events.add(new Event1DRefactor(segment.getMinX(), segment, EventType1D.START));
            events.add(new Event1DRefactor(segment.getMaxX(), segment, EventType1D.END));
        }

        return events;
    }

    private PriorityQueue<Event1DRefactor> prepareHorizontalEvents() {

        PriorityQueue<Event1DRefactor> events = new PriorityQueue<>();

        // There are no horizontal events if the rectangle is just a horizontal line

        // Add all rectangle vertical segments
        for(Rectangle rectangle : rectangles) {
            if(rectangle.isValid()) {
                Set<Segment1D> rectangleSegments = rectangle.getVerticalSegments();
                for (Segment1D segment : rectangleSegments) {
                    events.add(new Event1DRefactor(segment.getMinY(), rectangle, EventType1D.START_RECTANGLE));
                    events.add(new Event1DRefactor(segment.getMaxY(), rectangle, EventType1D.END_RECTANGLE));
                }
            }
        }
        // Polygon segments
        for(Segment1D segment : verticalSegments) {
            events.add(new Event1DRefactor(segment.getMinY(), segment, EventType1D.START));
            events.add(new Event1DRefactor(segment.getMaxY(), segment, EventType1D.END));
        }

        return events;
    }

    private long calculateArea(Point firstCorner, Point secondCorner) {
        long dx = Math.abs(firstCorner.x() - secondCorner.x()) + 1;
        long dy = Math.abs(firstCorner.y() - secondCorner.y()) + 1;

        return dx * dy;
    }
}
