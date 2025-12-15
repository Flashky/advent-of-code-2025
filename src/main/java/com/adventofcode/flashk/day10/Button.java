package com.adventofcode.flashk.day10;

import module java.base;
import lombok.Getter;

@Getter
public class Button {

    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\(([^)]+)\\)");

    private final List<Integer> toggles = new ArrayList<>();

    public Button(String input) {
        Matcher buttonMatcher = BUTTON_PATTERN.matcher(input);
        while(buttonMatcher.find()) {
            String group = buttonMatcher.group(1);
            String[] numbers = group.split(",");
            for(String number : numbers) {
                int index = Integer.parseInt(number);
                toggles.add(index);
            }
        }
    }

    public boolean[] press(boolean[] lights) {
        boolean[] outputLights = Arrays.copyOf(lights, lights.length);
        for(int toggleIndex : toggles) {
            outputLights[toggleIndex] = !lights[toggleIndex];
        }
        return outputLights;
    }

}
