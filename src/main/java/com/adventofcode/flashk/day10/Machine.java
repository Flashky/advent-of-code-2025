package com.adventofcode.flashk.day10;

import module java.base;

import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;

import java.util.List;

public class Machine {

    private static final char ON = '#';
    private static final Pattern JOLTAGE_PATTERN = Pattern.compile("\\{([^}]+)}");

    private boolean[] expectedLights;
    private final List<Button> buttons = new ArrayList<>();
    private List<Long> joltages = new ArrayList<>();

    public Machine(String input) {

        String[] splittedInput = input.split(" ");

        parseLights(splittedInput[0]);
        parseButtons(splittedInput);
        parseJoltages(splittedInput);


    }

    private void parseJoltages(String[] splittedInput) {

        String joltageInput = splittedInput[splittedInput.length-1];

        Matcher joltageMatcher = JOLTAGE_PATTERN.matcher(joltageInput);
        if(joltageMatcher.find()) {
            String group = joltageMatcher.group(1);
            String[] numbers = group.split(",");
            joltages = Arrays.stream(numbers).mapToLong(Long::parseLong).boxed().toList();
        }

    }

    private void parseLights(String lightInput) {
        char[] lightChars = lightInput.toCharArray();
        expectedLights = new boolean[lightChars.length-2];
        for(int i = 1; i < lightChars.length-1; i++) {
            expectedLights[i-1] = (lightChars[i] == ON);
        }
    }

    private void parseButtons(String[] splittedInput) {
        for(int i = 1; i < splittedInput.length - 1; i++) {
            buttons.add(new Button(splittedInput[i]));
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
        // Solves via Integer Linear Programming using oJalgo library
        return simplexOjAlgo();
    }

    private long simplexOjAlgo() {
        long result  = 0;
        ExpressionsBasedModel model = new ExpressionsBasedModel();

        Expression objective = model.objective();

        // Variables definition
        Map<Integer, Variable> createdVariables = new HashMap<>();

        // Objetive function: Minimize Z = x1 + x2 + ... + xn
        for (int i = 0; i < buttons.size(); i++) {
            // Define the variable
            Variable var = model.addVariable("x" + i).integer(true).lower(0).weight(1); // This weight(1) was really needed

            // Add variable to objective function
            objective.set(var, 1.0);

            // Save it in the map to reuse it at restrictions
            createdVariables.put(i, var);
        }

        for(int joltageIndex = 0; joltageIndex < joltages.size(); joltageIndex++) {

            Expression linearRestrictionJoltage = model.addExpression("j"+joltageIndex)
                                                        .level(joltages.get(joltageIndex));

            for(int buttonIndex = 0; buttonIndex < buttons.size(); buttonIndex++) {
                Button button = buttons.get(buttonIndex);
                if(button.getToggles().contains(joltageIndex)) {
                    Variable variable = createdVariables.get(buttonIndex);
                    linearRestrictionJoltage.set(variable, 1);
                }
            }

        }

        Optimisation.Result modelResult = model.minimise();

        for(int j = 0; j < buttons.size(); j++) {
            result += Math.round(modelResult.get(j).doubleValue());
        }


        return result;
    }

}
