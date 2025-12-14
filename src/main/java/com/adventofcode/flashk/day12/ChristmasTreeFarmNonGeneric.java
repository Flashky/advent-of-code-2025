package com.adventofcode.flashk.day12;

import static java.lang.IO.println;

import module java.base;

public class ChristmasTreeFarmNonGeneric {

    private final List<Region> regions;

    public ChristmasTreeFarmNonGeneric(List<String> inputs) {
        List<Present> presents = getPresents(inputs);
        regions = inputs.stream().skip(30).map(input -> new Region(input, presents)).toList();
    }

    private List<Present> getPresents(List<String> inputs) {
        // Split the first 30 input lines into fixed lists with size 5 so we can parse each present
        List<List<String>> presentInputs = inputs.stream().limit(30).gather(Gatherers.windowFixed(5)).toList();
        return presentInputs.stream().map(Present::new).toList();
    }

    /// Solves day 12 part 2 only for the input
    /// It can be done just with an spreadsheet...
    public long solve() {
        return regions.stream().filter(Region::canFit).count();
    }
}
