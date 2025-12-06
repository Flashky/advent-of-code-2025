package com.adventofcode.flashk.day06;

import module java.base;

public class Operation {

    private final static String SUM = "+";
    private final String operation;
    private final List<Integer> numbers = new ArrayList<>();

    public Operation(String operation) {
        this.operation = operation;
    }

    public void addNumber(int number) {
        numbers.add(number);
    }

    public long operate() {
        LongStream longStream = numbers.stream().mapToLong(Integer::longValue);
        return SUM.equals(operation) ? longStream.sum() : longStream.reduce(1, Math::multiplyExact);
    }
}
