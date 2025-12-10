package com.adventofcode.flashk.day09;

import static java.lang.IO.println;

import module java.base;
import com.adventofcode.flashk.common.Vector2;

public class MovieTheaterPolygon2 {

    private final List<Vector2> redTiles;
    private final Set<Vector2> edgePoints;
    private final Vector2[] edgePointsArray;
    private final Map<Vector2, Integer> windingNumbers = new HashMap<>();
    private final Map<Vector2, Boolean> inPolygon = new HashMap<>();

    public MovieTheaterPolygon2(List<String> inputs) {
        redTiles = inputs.stream().map(Vector2::new).toList();

        // Generate edge points
        Set<Vector2> allEdgePoints = new HashSet<>();
        int n = redTiles.size();
        for(int i = 0; i < n; i++) {
            // Take two consecutive points.
            // The module allows to pick the first one once at the last item
            Vector2 pointA = redTiles.get(i);
            Vector2 pointB = redTiles.get((i + 1) % n);
            allEdgePoints.addAll(generateEdgePoints(pointA, pointB));
        }

        edgePoints = allEdgePoints;
        edgePointsArray = edgePointSetToArray(allEdgePoints);

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
                result = Math.max(area, result);
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

                Set<Vector2> rectangleCorners = getRectangleEdgePoints(firstCorner, secondCorner);
                //Set<Vector2> rectanglePoints = calculateRectanglePoints(firstCorner, secondCorner);
                if(insidePolygon(rectangleCorners)) {
                    result = area;
                }
            }
        }

        return result;
    }

    private long calculateArea(Vector2 firstCorner, Vector2 secondCorner) {
        long dx = Math.abs(firstCorner.getX() - secondCorner.getX()) + 1;
        long dy = Math.abs(firstCorner.getY() - secondCorner.getY()) + 1;

        return dx * dy;
    }

    private Set<Vector2> generateEdgePoints(Vector2 firstPoint, Vector2 secondPoint) {

        Set<Vector2> edgePoints = new HashSet<>();
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
        return edgePoints;
    }

    private Vector2[] edgePointSetToArray(Set<Vector2> edgePoints) {
        Vector2[] edgePointsArray = new Vector2[edgePoints.size()];

        int i = 0;
        for(Vector2 edgePoint : edgePoints) {
            edgePointsArray[i++] = edgePoint;
        }

        return edgePointsArray;
    }

    private Set<Vector2> getRectangleEdgePoints(Vector2 firstCorner, Vector2 secondCorner) {

        Set<Vector2> result = new HashSet<>();

        Vector2 thirdCorner = new Vector2(firstCorner.getX(), secondCorner.getY());
        Vector2 fourthCorner = new Vector2(secondCorner.getX(), firstCorner.getY());


        // Calculate edges from 1 -> 3 and 4
        result.addAll(generateEdgePoints(firstCorner, thirdCorner));
        result.addAll(generateEdgePoints(firstCorner, fourthCorner));

        // Calculate edges from 2 -> 3 and 4
        result.addAll(generateEdgePoints(secondCorner, thirdCorner));
        result.addAll(generateEdgePoints(secondCorner, fourthCorner));

        //return result.toArray(new Vector2[0]);

        return result;

    }

    /// Calculates if the specified rectangle is inside the puzzle polygon.
    /// @return true if the rectangle specified by the 4 corners contained at the set is inside the polygon. false otherwise.
    private boolean insidePolygon(Set<Vector2> rectanglePoints) {
        for(Vector2 point : rectanglePoints) {

            if(inPolygon.containsKey(point)) {
                if(inPolygon.get(point)) {
                    continue;
                }
                return false;
            }

            // use winding number algorithm to check if every point is inside the original polygon
            int windingNumber;
            if(windingNumbers.containsKey(point)) {
                windingNumber = windingNumbers.get(point);
            } else {
                windingNumber = windingNumber(point);
                windingNumbers.put(point, windingNumber);
            }

            if(windingNumber <= 0) {
                inPolygon.put(point, false);
                return false;
            }

            inPolygon.put(point, true);
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
