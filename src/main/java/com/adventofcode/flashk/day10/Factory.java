package com.adventofcode.flashk.day10;

import module java.base;

public class Factory {

    private final List<Machine> machines;

    public Factory(List<String> inputs) {
        machines = inputs.stream().map(Machine::new).toList();
    }

    public long solveA() {
        return machines.stream().map(Machine::findMinimumPressesLight).mapToLong(Long::longValue).sum();
    }

    public long solveB() {
        return machines.stream().map(Machine::findMinimumPressesJoltage).mapToLong(Long::longValue).sum();
    }
}
