package com.adventofcode.flashk.day10;

import module java.base;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;

import java.util.List;

public class Machine {

    private static final char ON = '#';
    private static final Pattern JOLTAGE_PATTERN = Pattern.compile("\\{([^)]+)}");

    private final boolean[] expectedLights;
    private final List<Button> buttons = new ArrayList<>();
    private final List<Long> joltages = new ArrayList<>();

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
            buttons.add(new Button(splittedInput[i], expectedLights.length));
        }

        // Joltage
        String joltageInput = splittedInput[splittedInput.length-1];

        Matcher joltageMatcher = JOLTAGE_PATTERN.matcher(joltageInput);
        if(joltageMatcher.find()) {
            String group = joltageMatcher.group(1);
            String[] numbers = group.split(",");

            for(String number : numbers) {
                joltages.add(Long.parseLong(number));
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
        return simplexOjAlgo();
    }


    // TODO couldn't make this work, but I don't want to throw it away...
    private long simplexMath3() {

        // Objetive function
        double[] objectiveCoefficients = new double[buttons.size()];
        Arrays.fill(objectiveCoefficients, 1);
        LinearObjectiveFunction objectiveFunction = new LinearObjectiveFunction(objectiveCoefficients, 0);

        Set<LinearConstraint> allConstraints = new HashSet<>();

        // Restriction 1: build several equations such us x1 + x2 + x3 + ... + xn = joltages[i]
        for(int joltageIndex = 0; joltageIndex < joltages.size(); joltageIndex++) {

            double[] constraintsCoefficients = new double[buttons.size()];

            for(int buttonIndex = 0; buttonIndex < buttons.size(); buttonIndex++) {
                Button button = buttons.get(buttonIndex);
                if(button.getToggles().contains(joltageIndex)) {
                    constraintsCoefficients[buttonIndex]++;
                }
            }

            allConstraints.add(new LinearConstraint(constraintsCoefficients, Relationship.EQ, joltages.get(joltageIndex)));
        }

        // Build the constraint set
        LinearConstraintSet linearConstraintSet = new LinearConstraintSet(allConstraints);

        SimplexSolver solver = new SimplexSolver();

        PointValuePair pointValuePair = solver.optimize(objectiveFunction,
                                linearConstraintSet,
                                GoalType.MINIMIZE,
                                new NonNegativeConstraint(true),
                                PivotSelectionRule.BLAND); // Default DANTZIG


        double[] first =  pointValuePair.getFirst();
        double second = pointValuePair.getSecond();

        long roundFirst = 0;
        for(double value : first) {
            roundFirst += (long) Math.round(value);
        }
        return roundFirst;

        //return (long) Math.round(pointValuePair.getValue());


    }


    private long simplexOjAlgo() {
        long result  = 0;
        ExpressionsBasedModel model = new ExpressionsBasedModel();

        Expression objective = model.objective();

        // Variables definition
        Map<Integer, Variable> createdVariables = new HashMap<>();

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
            result += (long) Math.round(modelResult.get(j).doubleValue());
        }


        return result;
    }

}
