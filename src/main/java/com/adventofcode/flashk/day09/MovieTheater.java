package com.adventofcode.flashk.day09;

import static java.lang.IO.println;

import module java.base;
import com.adventofcode.flashk.common.Vector2;
import org.apache.commons.math3.util.MathUtils;

public class MovieTheater {

    private final List<Vector2> redTiles;

    private final Set<Vector2> edgePoints = new HashSet<>();
    private Vector2[] edgePointsArray;
    private final Map<Vector2,Boolean> pointInPolygon = new HashMap<>();

    //private final int minX;
    //private final int maxX;

    public MovieTheater(List<String> inputs) {
        redTiles = inputs.stream().map(Vector2::new).toList();
        calculateEdgePoints();

        println("Edge points calculated");
    }

    public long solveA() {
        long result = Long.MIN_VALUE;

        // Buscar en cada par de coordenadas el rectángulo más grande.
        for(int i = 0; i < redTiles.size(); i++) {
            Vector2 firstCorner = redTiles.get(i);

            for(int j = i+1; j < redTiles.size(); j++) {
                Vector2 secondCorner = redTiles.get(j);
                long area = calculateArea(firstCorner, secondCorner);
                if(area > result) {
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
                if(area <= result) {
                    continue;
                }
                Set<Vector2> rectanglePoints = calculateRectanglePoints(firstCorner, secondCorner);
                if(insidePolygon(rectanglePoints)) {
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

    /// Calculates if all rectangle points are inside of the puzzle polygon.
    /// @return true if every point is inside the polygon. false otherwise.
    private boolean insidePolygon(Set<Vector2> rectanglePoints) {
        for(Vector2 point : rectanglePoints) {
            // use winding number algorithm

            if(!insidePolygon(point)) {
                return false;
            }
        }
        return true;
    }

    /// Verifies if a single point is inside of the puzzle polygon.
    private boolean insidePolygon(Vector2 point) {
        if(edgePoints.contains(point)) {
            return true;
        }

        if(pointInPolygon.containsKey(point)) {
            return pointInPolygon.get(point);
        }

        // Calculamos un punto P que va a estar en la misma horizontal, pero en otra posición vertical
        int px = point.getX() + 1;
        int py = point.getY();
        int m = 0; // El slope de una recta horizontal es 0

        // Idea 1:
        // En lugar de trazar una recta y buscar intersecciones, compruebo cada punto comparado con puntos que
        // estén en su misma horizontal.
        // Para que el punto esté dentro del rectángulo se tienen que dar las condiciones:
        // - Las coordenadas Y han de ser iguales (misma recta)
        // - La coordenada del punto ha de ser menor que la del eje (parar hacer raycasting únicamente a la derecha).
        // - El número de puntos cruzados debe ser impar

        int count = 0;
        for(Vector2 edgePoint : edgePoints) {
            if(edgePoint.getY() == point.getY()) {
                if(point.getX() < edgePoint.getX()) {
                    count++;
                }
            }
        }

        boolean isOdd = count % 2 != 0;
        if(isOdd) {
            pointInPolygon.put(point, true);
        } else {
            pointInPolygon.put(point, false);
        }
        return isOdd;
    }

    private int windingNumber(Vector2 point) {

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
                    // If vertical, isLeft will tell if point is at the left of points A nd B
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
