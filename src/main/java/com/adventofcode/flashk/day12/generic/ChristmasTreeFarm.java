package com.adventofcode.flashk.day12.generic;

import static java.lang.IO.println;

import module java.base;

public class ChristmasTreeFarm {

    private final List<Present> presents;
    private final List<Region> regions;

    public ChristmasTreeFarm(List<String> inputs) {

        List<List<String>> presentInputs = inputs.stream().limit(30).gather(Gatherers.windowFixed(5)).toList();
        presents = presentInputs.stream().map(Present::new).toList();

        regions = inputs.stream().skip(30).map(Region::new).toList();

    }

    public long solveA() {
        long result = 0;
        for(Region region : regions) {
            if(region.canFit(presents)) {
                result++;
            }
        }
        return result;
    }
}
