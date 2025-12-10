package com.adventofcode.flashk.day10;

import module java.base;
import lombok.Getter;

@Getter
public class Button {

    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\(([^)]+)\\)");

    private final List<Integer> toggles = new ArrayList<>();
    private final long multiplier ;

    public Button(String input, int counterSize) {
        Matcher buttonMatcher = BUTTON_PATTERN.matcher(input);
        long multiplier = 0;
        while(buttonMatcher.find()) {
            String group = buttonMatcher.group(1);
            String[] numbers = group.split(",");
            for(String number : numbers) {
                int index = Integer.parseInt(number);
                toggles.add(index);
                multiplier += Math.powExact(10L, counterSize-index-1);
            }
        }

        this.multiplier = multiplier;

    }

    public boolean[] press(boolean[] lights) {
        boolean[] outputLights = Arrays.copyOf(lights, lights.length);
        for(int toggleIndex : toggles) {
            outputLights[toggleIndex] = !lights[toggleIndex];
        }
        return outputLights;
    }

    public int[] press(int[] counters) {
        int[] outputCounters = Arrays.copyOf(counters, counters.length);
        for(int toggleIndex : toggles) {
            outputCounters[toggleIndex]++;
        }
        return outputCounters;
    }
}
