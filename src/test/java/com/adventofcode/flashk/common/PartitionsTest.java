package com.adventofcode.flashk.common;

import static org.junit.jupiter.api.Assertions.*;

import module java.base;
import org.junit.jupiter.api.Test;

class PartitionsTest {

    @Test
    void fromStringPartitionSize1() {

        List<String> result = Partitions.fromString("abcd", 1);

        assertEquals(4, result.size());
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
        assertEquals("d", result.get(3));
    }

    @Test
    void fromStringPartitionSize2() {

        List<String> result = Partitions.fromString("abcd", 2);

        assertEquals(2, result.size());
        assertEquals("ab", result.get(0));
        assertEquals("cd", result.get(1));

    }

    @Test
    void fromStringPartitionSize3() {
        List<String> result = Partitions.fromString("abcd", 3);

        assertEquals(2, result.size());
        assertEquals("abc", result.get(0));
        assertEquals("d", result.get(1));
    }

    @Test
    void fromStringPartitionSize4() {
        List<String> result = Partitions.fromString("abcd", 4);

        assertEquals(1, result.size());
        assertEquals("abcd", result.get(0));
    }

}