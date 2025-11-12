package com.adventofcode.flashk.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Array2DUtilTest {

    @Test
    void transposeTest() {
        char[][] array = {
                {'a', 'b', 'c'},
                {'d', 'e', 'f'},
                {'g', 'h', 'i'}
        };

        char[][] expected = {
                {'a', 'd', 'g'},
                {'b', 'e', 'h'},
                {'c', 'f', 'i'}
        };

        char[][] actual = Array2DUtil.transpose(array);
        Array2DUtil.paint(array);
        assertArrayEquals(expected, actual);
    }
}