package com.adventofcode.flashk.day11;

import module java.base;
import org.apache.commons.lang3.StringUtils;

public class Reactor {

    private static final String START = "you";
    private static final String END = "out";

    private final Map<String,List<String>> graph = new HashMap<>();

    public Reactor(List<String> inputs){
        for(String input : inputs) {
            String[] splittedInput = input.split(":");

            String device = splittedInput[0];
            List<String> outputDevices = new ArrayList<>();

            String[] splittedOutputs = splittedInput[1].split(StringUtils.SPACE);
            for(String splittedOutput : splittedOutputs) {
                if(!StringUtils.EMPTY.equals(splittedOutput)) {
                    outputDevices.add(splittedOutput);
                }
            }

            graph.put(device, outputDevices);
        }
    }

    public long solveA() {
        return dfs(START);
    }

    private long dfs(String node) {
        if(END.equals(node)) {
            return 1;
        }

        long result = 0;
        List<String> adjacents = graph.get(node);
        for(String adjacent : adjacents) {
            result += dfs(adjacent);
        }

        return result;
    }

}
