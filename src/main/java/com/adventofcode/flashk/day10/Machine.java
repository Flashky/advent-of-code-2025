package com.adventofcode.flashk.day10;

import static java.lang.IO.println;

import module java.base;

public class Machine {

    private static final char ON = '#';
    private static final char OFF = '.';
    private static final Pattern JOLTAGE_PATTERN = Pattern.compile("\\{([^\\)]+)\\}");

    private final boolean[] expectedLights;
    private final List<Button> buttons = new ArrayList<>();
    private final List<Integer> joltages = new ArrayList<>();

    public Machine(String input) {

        String[] splittedInput = input.split(" ");

        // Lights
        char[] lightChars = splittedInput[0].toCharArray();
        expectedLights = new boolean[lightChars.length-2];
        for(int i = 1; i < lightChars.length-1; i++) {
            expectedLights[i-1] = (lightChars[i] == ON);
        }

        // Buttons
        for(int i = 1; i < splittedInput.length - 1; i++) {
            buttons.add(new Button(splittedInput[i]));
        }

        // Joltage
        String joltageInput = splittedInput[splittedInput.length-1];

        Matcher joltageMatcher = JOLTAGE_PATTERN.matcher(joltageInput);
        if(joltageMatcher.find()) {
            String group = joltageMatcher.group(1);
            String[] numbers = group.split(",");
            for(String number : numbers) {
                joltages.add(Integer.parseInt(number));
            }
        }
    }

    public long findMinimumPressesLight() {
        long buttonPresses = Long.MAX_VALUE;

        // Solve via BFS

        // Initial light status: no button presses and all lights are off
        LightStatus root = new LightStatus(0, new boolean[expectedLights.length]);

        // Add root to the queue
        Queue<LightStatus> lightStatusQueue = new ArrayDeque<>();
        lightStatusQueue.add(root);

        // Mark root as visited
        Set<Integer> visitedStatuses = new HashSet<>();
        visitedStatuses.add(Arrays.hashCode(root.lights()));

        while(!lightStatusQueue.isEmpty()) {
            LightStatus currentLightStatus = lightStatusQueue.poll();

            // Exit condition
            if(isSolution(currentLightStatus.lights())) {
                return currentLightStatus.numberOfPresses();
            }

            // Generate next level of the tree
            int currentPresses = currentLightStatus.numberOfPresses();
            boolean[] currentLights = currentLightStatus.lights();
            for(Button button : buttons) {

                boolean[] newLights = button.press(currentLights);
                LightStatus newLightStatus = new LightStatus(currentPresses+1, newLights);
                int statusHashCode = Arrays.hashCode(newLights);

                // Prune any already visited statuses
                if(!visitedStatuses.contains(statusHashCode)) {
                    visitedStatuses.add(statusHashCode);
                    lightStatusQueue.add(newLightStatus);
                }

            }

        }

        return buttonPresses;
    }

    private boolean isSolution(boolean[] outputLights) {
        return Arrays.equals(expectedLights, outputLights);
    }

    public long findMinimumPressesJoltage() {
        long result = Long.MAX_VALUE;

        return result;
    }

}
