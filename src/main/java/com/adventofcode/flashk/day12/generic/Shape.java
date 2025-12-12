package com.adventofcode.flashk.day12.generic;

import static java.lang.IO.println;

import module java.base;
import com.adventofcode.flashk.common.Array2DUtil;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
public class Shape {

    public static final int PRESENT_SIZE = 3;
    private char[][] matrix = new char[PRESENT_SIZE][PRESENT_SIZE];

    public Shape(List<String> input) {
        List<String> shapeLines = input.stream().skip(1).limit(PRESENT_SIZE).toList();

        int i = 0;
        for(String shapeLine : shapeLines) {
            matrix[i++] = shapeLine.toCharArray();
        }

    }

    public Shape(char[][] matrix) {
        this.matrix = matrix;
    }

    public List<Shape> getVariations() {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(this);

        Shape currentShape = this;
        for(int i = 0; i < PRESENT_SIZE; i++) {
            // Rotate and add normal shape
            Shape rotatedShape = new Shape(Array2DUtil.rotate(currentShape.matrix));
            shapes.add(rotatedShape);
            currentShape = rotatedShape;
        }

        // Flip and calculate flipped shapes
        Shape flippedShape = new Shape(Array2DUtil.transpose(matrix));
        shapes.add(flippedShape);

        Shape currentFlippedShape = flippedShape;
        for(int i = 0; i < PRESENT_SIZE; i++) {

            // Rotate and add flipped shape
            Shape rotatedFlippedShape = new Shape(Array2DUtil.rotate(currentFlippedShape.matrix));
            shapes.add(rotatedFlippedShape);
            currentFlippedShape = rotatedFlippedShape;
        }


        return shapes;
    }
}
