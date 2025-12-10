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
    private final int[] expectedJoltages;
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

        expectedJoltages = joltages.stream().mapToInt(Integer::intValue).toArray();
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
        long buttonPresses = Long.MAX_VALUE;

        // Solve via BFS

        // Initial light status: no button presses and all lights are off
        JoltageStatus root = new JoltageStatus(0, new int[joltages.size()]);

        // Add root to the queue
        Queue<JoltageStatus> joltageStatusQue = new ArrayDeque<>();
        joltageStatusQue.add(root);

        // Mark root as visited
        Set<Integer> visitedStatuses = new HashSet<>();
        visitedStatuses.add(Arrays.hashCode(root.counters()));

        while(!joltageStatusQue.isEmpty()) {
            JoltageStatus currentJoltageStatus = joltageStatusQue.poll();

            // Exit condition
            if(isSolution(currentJoltageStatus.counters())) {
                return currentJoltageStatus.numberOfPresses();
            }

            // Generate next level of the tree
            int currentPresses = currentJoltageStatus.numberOfPresses();
            int[] currentJoltages = currentJoltageStatus.counters();

            for(Button button : buttons) {

                int[] newJoltages = button.press(currentJoltages);

                int statusHashCode = Arrays.hashCode(newJoltages);

                // Prune joltages if they are greated than the max allowed
                if((!visitedStatuses.contains(statusHashCode)) && (isValidJoltage(newJoltages))) {
                    JoltageStatus newJoltageStatus = new JoltageStatus(currentPresses+1, newJoltages);
                    visitedStatuses.add(statusHashCode);
                    joltageStatusQue.add(newJoltageStatus);
                }

            }

        }

        return buttonPresses;
    }

    public long findMinimumPressesJoltageRecursive() {
        return findMinimumPressesJoltageRecursive(0, new int[joltages.size()]);
    }

    private long findMinimumPressesJoltageRecursive(int numberPresses, int[] counters) {

        if(isSolution(counters)) {
            return numberPresses;
        } else if(!isValidJoltage(counters)) {
            return Long.MAX_VALUE;
        }

        long minimumPreses = Long.MAX_VALUE;

        for(Button button: buttons) {
            int[] counterUpdate = button.press(counters);
            long currentPresses = findMinimumPressesJoltageRecursive(numberPresses+1, counterUpdate);
            if(currentPresses < minimumPreses) {
                minimumPreses = currentPresses;
            }
        }

        return minimumPreses;

    }

    private boolean isValidJoltage(int[] newJoltages) {
        for(int i = 0; i < newJoltages.length; i++) {
            if(newJoltages[i] > expectedJoltages[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isSolution(int[] outputJoltages) {
        return Arrays.equals(expectedJoltages, outputJoltages);
    }


}
