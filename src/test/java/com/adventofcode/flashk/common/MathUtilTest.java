package com.adventofcode.flashk.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.function.DoubleFunction;
import java.util.function.IntFunction;
import java.util.function.LongFunction;

class MathUtilTest {


    @Test
    void testSummationWithIntFunction() {

        // Example function: f(i) = i
        IntFunction<Integer> function = i -> i;
        assertEquals(0, MathUtil.summation(0, 0, function));
        assertEquals(1, MathUtil.summation(1, 1, function));
        assertEquals(15, MathUtil.summation(1, 5, function));

        // Example function: f(i) = i^2
        function = i -> i * i;
        assertEquals(55, MathUtil.summation(1, 5, function));

        // Example function: f(i) = (i-3)^2
        function = i -> (int) Math.pow(i-3, 2);
        assertEquals(2567900, MathUtil.summation(1, 200, function));

        // Example function: f(i) = i^3 - i^2
        function = i -> (int) Math.pow(i,3) - (int) Math.pow(i,2);
        assertEquals(350, MathUtil.summation(1, 6, function));
    }

    @Test
    void testSummationWithLongFunction() {

        // Example function: f(i) = i
        LongFunction<Long> function = i -> i;
        assertEquals(0, MathUtil.summation(0, 0, function));
        assertEquals(1, MathUtil.summation(1, 1, function));
        assertEquals(15, MathUtil.summation(1, 5, function));

        // Example function: f(i) = i^2
        function = i -> i * i;
        assertEquals(55, MathUtil.summation(1, 5, function));

        // Example function: f(i) = (i-3)^2
        function = i -> (long) Math.pow(i-3, 2);
        assertEquals(2567900, MathUtil.summation(1, 200, function));

        // Example function: f(i) = i^3 - i^2
        function = i -> (long) Math.pow(i,3) - (long) Math.pow(i,2);
        assertEquals(350, MathUtil.summation(1, 6, function));
    }

    @Test
    void testSummationWithDoubleFunction() {

        // Example function: f(i) = i
        DoubleFunction<Double> function = i -> i;
        assertEquals(0, MathUtil.summation(0, 0, function));
        assertEquals(1, MathUtil.summation(1, 1, function));
        assertEquals(15, MathUtil.summation(1, 5, function));

        // Example function: f(i) = i^2
        function = i -> i * i;
        assertEquals(55, MathUtil.summation(1, 5, function));

        // Example function: f(i) = (i-3)^2
        function = i -> Math.pow(i-3, 2);
        assertEquals(2567900, MathUtil.summation(1, 200, function));

        // Example function: f(i) = i^3 - i^2
        function = i -> Math.pow(i,3) - Math.pow(i,2);
        assertEquals(350, MathUtil.summation(1, 6, function));
    }

    @Test
    void testProductWithIntFunction() {

        // Example function: f(i) = i
        IntFunction<Integer> function = i -> i;
        assertEquals(0, MathUtil.product(0, 0, function));
        assertEquals(1, MathUtil.product(1, 1, function));
        assertEquals(120, MathUtil.product(1, 5, function));

        // Example function: f(i) = 2*i
        function = i -> 2 * i;
        assertEquals(384, MathUtil.product(1,4, function));

        //  Example function: f(i) = i^2
        function = i -> i * i;
        assertEquals(14400, MathUtil.product(1, 5, function));

    }

    @Test
    void testProductWithLongFunction() {

        // Example function: f(i) = i
        LongFunction<Long> function = i -> i;
        assertEquals(0, MathUtil.product(0, 0, function));
        assertEquals(1, MathUtil.product(1, 1, function));
        assertEquals(120, MathUtil.product(1, 5, function));

        // Example function: f(i) = 2*i
        function = i -> 2 * i;
        assertEquals(384, MathUtil.product(1,4, function));

        //  Example function: f(i) = i^2
        function = i -> i * i;
        assertEquals(14400, MathUtil.product(1, 5, function));

    }
}
