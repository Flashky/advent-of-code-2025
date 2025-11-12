package com.adventofcode.flashk.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseUtilTest {

    @Test
    void binaryToDecIntegerTest() {
        assertEquals(0, BaseUtil.binaryToDecInteger("0"));
        assertEquals(1, BaseUtil.binaryToDecInteger("1"));
        assertEquals(2, BaseUtil.binaryToDecInteger("10"));
        assertEquals(5, BaseUtil.binaryToDecInteger("101"));
        assertEquals(8, BaseUtil.binaryToDecInteger("1000"));
        assertEquals(10, BaseUtil.binaryToDecInteger("1010"));
        assertEquals(15, BaseUtil.binaryToDecInteger("1111"));
    }

    @Test
    void binaryToDecTest() {
        assertEquals(0, BaseUtil.binaryToDec("0"));
        assertEquals(1, BaseUtil.binaryToDec("1"));
        assertEquals(2, BaseUtil.binaryToDec("10"));
        assertEquals(5, BaseUtil.binaryToDec("101"));
        assertEquals(8, BaseUtil.binaryToDec("1000"));
        assertEquals(10, BaseUtil.binaryToDec("1010"));
        assertEquals(15, BaseUtil.binaryToDec("1111"));
    }

    @Test
    void hexToBinaryTest() {
        assertEquals("0", BaseUtil.hexToBinary("0"));
        assertEquals("1", BaseUtil.hexToBinary("1"));
        assertEquals("10", BaseUtil.hexToBinary("2"));
        assertEquals("101", BaseUtil.hexToBinary("5"));
        assertEquals("1000", BaseUtil.hexToBinary("8"));
        assertEquals("1010", BaseUtil.hexToBinary("A"));
        assertEquals("1011", BaseUtil.hexToBinary("B"));
        assertEquals("1100", BaseUtil.hexToBinary("C"));
        assertEquals("1101", BaseUtil.hexToBinary("D"));
        assertEquals("1110", BaseUtil.hexToBinary("E"));
        assertEquals("1111", BaseUtil.hexToBinary("F"));
        assertEquals("100110110", BaseUtil.hexToBinary("136"));
    }

    @Test
    void hexToBinaryPadLeft() {
        assertEquals("0000", BaseUtil.hexToBinaryPadLeft("0"));
        assertEquals("0001", BaseUtil.hexToBinaryPadLeft("1"));
        assertEquals("0010", BaseUtil.hexToBinaryPadLeft("2"));
        assertEquals("0101", BaseUtil.hexToBinaryPadLeft("5"));
        assertEquals("1000", BaseUtil.hexToBinaryPadLeft("8"));
        assertEquals("1010", BaseUtil.hexToBinaryPadLeft("A"));
        assertEquals("1011", BaseUtil.hexToBinaryPadLeft("B"));
        assertEquals("1100", BaseUtil.hexToBinaryPadLeft("C"));
        assertEquals("1101", BaseUtil.hexToBinaryPadLeft("D"));
        assertEquals("1110", BaseUtil.hexToBinaryPadLeft("E"));
        assertEquals("1111", BaseUtil.hexToBinaryPadLeft("F"));
        assertEquals("000100110110", BaseUtil.hexToBinaryPadLeft("136"));
    }
}