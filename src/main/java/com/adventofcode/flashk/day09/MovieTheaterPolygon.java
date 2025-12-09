package com.adventofcode.flashk.day09;

import static java.lang.IO.println;

import module java.base;
import com.adventofcode.flashk.common.Vector2;

public class MovieTheaterPolygon {

    private final List<Vector2> redTiles;
    private final Set<Vector2> edgePoints = new HashSet<>();
    private Vector2[] edgePointsArray;
    private final Map<Vector2, Integer> windingNumbers = new HashMap<>();
    private final Map<Vector2, Boolean> inPolygon = new HashMap<>();

    public MovieTheaterPolygon(List<String> inputs) {
        redTiles = inputs.stream().map(Vector2::new).toList();
        calculateEdgePoints();

        println("Edge points calculated");
    }

    public long solveA() {
        long result = Long.MIN_VALUE;

        // Buscar en cada par de coordenadas el rectángulo más grande.
        for (int i = 0; i < redTiles.size(); i++) {
            Vector2 firstCorner = redTiles.get(i);

            for (int j = i + 1; j < redTiles.size(); j++) {
                Vector2 secondCorner = redTiles.get(j);
                long area = calculateArea(firstCorner, secondCorner);
                if (area > result) {
                    result = area;
                }
            }
        }

        return result;
    }

    public long solveB() {
        long result = Long.MIN_VALUE;

        // Buscar en cada par de coordenadas el rectángulo más grande.
        for(int i = 0; i < redTiles.size(); i++) {
            Vector2 firstCorner = redTiles.get(i);

            for(int j = i+1; j < redTiles.size(); j++) {
                Vector2 secondCorner = redTiles.get(j);
                long area = calculateArea(firstCorner, secondCorner);

                // Skip heavier calculations if the rectangle area is not going to be better than the optimal solution
                if(area <= result) {
                    continue;
                }

                Set<Vector2> rectangleCorners = getRectangleCorners(firstCorner, secondCorner);
                //Set<Vector2> rectanglePoints = calculateRectanglePoints(firstCorner, secondCorner);
                if(insidePolygon(rectangleCorners)) {
                    result = area;
                }
            }
        }

        return result;
    }

    private long calculateArea(Vector2 firstCorner, Vector2 secondCorner) {
        long dx;
        long dy;

        if(firstCorner.getX() > secondCorner.getX()) {
            dx = firstCorner.getX() - secondCorner.getX();
        } else {
            dx = secondCorner.getX() - firstCorner.getX();
        }

        if(firstCorner.getY() > secondCorner.getY()) {
            dy = firstCorner.getY() - secondCorner.getY();
        } else {
            dy = secondCorner.getY() - firstCorner.getY();
        }

        dx++;
        dy++;

        return Math.abs(dx) * Math.abs(dy);
    }

    private void calculateEdgePoints() {

        Vector2 firstPoint = redTiles.getFirst();
        for(int i = 1; i < redTiles.size(); i++) {
            Vector2 secondPoint = redTiles.get(i);
            addEdgePoints(firstPoint, secondPoint);
            firstPoint = secondPoint;
        }

        Vector2 secondPoint = redTiles.getFirst();
        addEdgePoints(firstPoint, secondPoint);

        edgePointsArray = new Vector2[edgePoints.size()];
        int i = 0;
        for(Vector2 edgePoint : edgePoints) {
            edgePointsArray[i++] = edgePoint;
        }
    }

    private void addEdgePoints(Vector2 firstPoint, Vector2 secondPoint) {

        edgePoints.add(firstPoint);
        edgePoints.add(secondPoint);

        if(firstPoint.getX() == secondPoint.getX()) {

            // Same vertical
            int minY = Math.min(firstPoint.getY(), secondPoint.getY());
            int maxY = Math.max(firstPoint.getY(), secondPoint.getY());

            // Add all edge points
            for(int y = minY+1; y < maxY; y++) {
                edgePoints.add(new Vector2(firstPoint.getX(), y));
            }

        } else {

            // Same horizontal
            int minX = Math.min(firstPoint.getX(), secondPoint.getX());
            int maxX = Math.max(firstPoint.getX(), secondPoint.getX());

            // Add all edge points
            for(int x = minX+1; x < maxX; x++) {
                edgePoints.add(new Vector2(x, firstPoint.getY()));
            }
        }
    }


    /// Calculates if the specified rectangle is inside the puzzle polygon.
    /// @return true if the rectangle specified by the 4 corners contained at the set is inside the polygon. false otherwise.
    private boolean insidePolygon(Set<Vector2> rectanglePoints) {
        for(Vector2 point : rectanglePoints) {

            // use winding number algorithm to check if all 4 corners of the rectangle are inside the polygon
            int windingNumber;
            if(windingNumbers.containsKey(point)) {
                windingNumber = windingNumbers.get(point);
            } else {
                windingNumber = windingNumber(point);
                windingNumbers.put(point, windingNumber);
            }


            if(windingNumber <= 0) {
                return false;
            }
            /*
            boolean isInPolygon;
            if(inPolygon.containsKey(point)) {
                isInPolygon = inPolygon.get(point);
            } else {
                isInPolygon = isInPolygon(point);
                inPolygon.put(point, isInPolygon);

            }
            if(!isInPolygon) {
                return false;
            }*/
        }
        return true;
    }

    private int windingNumber(Vector2 point) {

        if(edgePoints.contains(point)) {
            return 1;
        }

        int wn = 0;
        int n = edgePoints.size();

        // This logic works even with horizontal and vertical edges

        // Traverse the polygon points
        for(int i = 0; i < n; i++) {
            // Take two consecutive points.
            // The module allows to pick the first one once at the last item
            Vector2 pointA = edgePointsArray[i];
            Vector2 pointB = edgePointsArray[(i+1) % n];


            if(pointA.getY() <= point.getY()) {
                if(pointB.getY() > point.getY()) {
                    if(isLeft(pointA, pointB, point) > 0)  {
                        wn++;
                    }
                }
            } else {
                if(pointB.getY() <= point.getY()) {
                    if(isLeft(pointA, pointB, point) < 0) {
                        wn--;
                    }
                }
            }
        }

        return wn;

    }

    private Set<Vector2> getRectangleCorners(Vector2 firstCorner, Vector2 secondCorner) {

        Set<Vector2> corners = new HashSet<>();

        corners.add(firstCorner);
        corners.add(secondCorner);
        corners.add(new Vector2(firstCorner.getX(), secondCorner.getY()));
        corners.add(new Vector2(secondCorner.getX(), firstCorner.getY()));

        return corners;

    }

    private List<Vector2> getRectangleCornersList(Vector2 firstCorner, Vector2 secondCorner) {

        List<Vector2> corners = new ArrayList<>();

        corners.add(firstCorner);
        corners.add(secondCorner);
        corners.add(new Vector2(firstCorner.getX(), secondCorner.getY()));
        corners.add(new Vector2(secondCorner.getX(), firstCorner.getY()));

        return corners;

    }



    // Esto es demasiado pesado
    private Set<Vector2> calculateRectanglePoints(Vector2 firstPoint, Vector2 secondPoint) {
        Set<Vector2> rectanglePoints = new HashSet<>();

        int minX = Math.min(firstPoint.getX(), secondPoint.getX());
        int maxX = Math.max(firstPoint.getX(), secondPoint.getX());
        int minY = Math.min(firstPoint.getY(), secondPoint.getY());
        int maxY = Math.max(firstPoint.getY(), secondPoint.getY());

        for(int x = minX; x <= maxX; x++) {
            for(int y = minY; y <= maxY; y++) {
                rectanglePoints.add(new Vector2(x,y));
            }
        }

        return rectanglePoints;
    }

    private boolean isInPolygon(Vector2 point) {
        boolean inPolygon = false;
        int n = edgePoints.size();

        for(int i = 0; i < n; i++) {
            // Take two consecutive points.
            // The module allows to pick the first one once at the last item
            Vector2 pointA = edgePointsArray[i];
            Vector2 pointB = edgePointsArray[(i + 1) % n];

            if(pointA.getX() == point.getX() && pointA.getY() == point.getY()) {
                return true;
            }

            if((pointA.getY() > point.getY()) != (pointB.getY() > point.getY())) {
                int deltaXba = pointB.getX() - pointA.getX();
                int deltaYpa = point.getY() - pointA.getY();
                int deltaXpa = point.getX() - pointA.getX();
                int deltaYba = pointB.getY() - pointA.getY();
                //  (x - ax) * (by - ay) - (bx - ax) * (y - ay)
                // deltaXba * deltaYpa
                double slope = (deltaXpa * deltaYba) - (deltaXba * deltaYpa);
                if(slope == 0) {
                    return true;
                }

                if((slope < 0) != (pointB.getY() < pointA.getY())) {
                    inPolygon = !inPolygon;
                }
            }
        }

        return inPolygon;
    }

    /// Calculates if the specified point is at the left of the segment conformed by points A and B:
    /// Positive sign: P is at the left of the AB segment.
    /// Negative sign: P is at the right of the AB segment;
    /// 0: P is on the same line as the AB segment.
    private double isLeft(Vector2 pointA, Vector2 pointB, Vector2 point) {
        int deltaXba = pointB.getX() - pointA.getX();
        int deltaYpa = point.getY() - pointA.getY();
        int deltaXpa = point.getX() - pointA.getX();
        int deltaYba = pointB.getY() - pointA.getY();

        return (deltaXba * deltaYpa) - (deltaXpa * deltaYba);

    }
}
