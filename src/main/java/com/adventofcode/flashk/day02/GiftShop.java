package com.adventofcode.flashk.day02;

import module java.base;

public class GiftShop {

    private final List<Range> ranges;

    public GiftShop(String input) {
        String[] ranges = input.split(",");
        this.ranges = Arrays.stream(ranges).map(Range::new).toList();
    }

    public long solveA(){
        return ranges.stream().mapToLong(Range::calculateInvalidIdSum).sum();
    }

    public long solveB() {
        return ranges.stream().mapToLong(Range::calculateInvalidIdSumMulti).sum();
    }
}
