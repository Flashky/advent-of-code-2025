package com.adventofcode.flashk.day11;

import static java.lang.IO.println;

import module java.base;
import org.apache.commons.lang3.StringUtils;

public class Reactor {

    private static final String YOU = "you";
    private static final String END = "out";
    private static final String SERVER = "svr";
    private static final String DAC = "dac";
    private static final String FFT = "fft";

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
        return dfs(YOU);
    }

    public long solveB() {
        long result = 0;

        // Search all possible paths from you
        // dfs

        result = dfs(SERVER,false,false);
        return result;
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

    private long dfs(String node, boolean hasPassedDac, boolean hasPassedFft) {
        if(END.equals(node)) {
            if(hasPassedDac && hasPassedFft) {
                return 1;
            } else {
                return 0;
            }
        }

        long result = 0;
        List<String> adjacents = graph.get(node);
        for(String adjacent : adjacents) {
            boolean nexPassedDac = hasPassedDac || DAC.equals(adjacent);
            boolean nexPassedFft = hasPassedFft || FFT.equals(adjacent);
            result += dfs(adjacent, nexPassedDac, nexPassedFft);
        }

        return result;
    }

}
