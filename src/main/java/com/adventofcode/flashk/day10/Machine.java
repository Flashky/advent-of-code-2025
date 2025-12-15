package com.adventofcode.flashk.day10;

import static java.lang.IO.println;

import module java.base;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
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
            //StringBuilder joltageNumberBuilder = new StringBuilder();

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
        long result = ojAlgo();

        return result;
    }


    private long simplex() {

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
                                new NonNegativeConstraint(true));

        return (long) Math.ceil(pointValuePair.getValue());

    }


    private long ojAlgo() {
        long result  = 0;
        ExpressionsBasedModel model = new ExpressionsBasedModel();

        Expression objective = model.objective();

        // Variables definition
        Map<Integer, Variable> createdVariables = new HashMap<>();

        for (int i = 0; i < buttons.size(); i++) {
            // Define the variable
            Variable var = model.addVariable("x" + i).integer(true).lower(0);

            // Add variable to objective function
            objective.set(var, 1.0);

            // Save it in the map to reuse it at restrictions
            createdVariables.put(i, var);
        }

        for(int joltageIndex = 0; joltageIndex < joltages.size(); joltageIndex++) {

            //final BigDecimal MIN_SUM = new BigDecimal(19830); // x1 + x2 + ... >= 19835 (de 19834 + 1)
            //final BigDecimal MAX_SUM = new BigDecimal(19885); // x1 + x2 + ... <= 19877 (de 19878 - 1)
            //BigDecimal joltageValue = BigDecimal.valueOf(joltages.get(joltageIndex));

            Expression linearRestrictionJoltage = model.addExpression("j"+joltageIndex)
                                                        .level(joltages.get(joltageIndex));

            //final BigDecimal TOLERANCIA_JOLTAGE = BigDecimal.ONE;
            //long joltageLongValue = joltages.get(joltageIndex);
            // Conversión a BigDecimal
            //BigDecimal joltageValue = BigDecimal.valueOf(joltageLongValue);
            //linearRestrictionJoltage.lower(joltageValue.subtract(TOLERANCIA_JOLTAGE));
            //linearRestrictionJoltage.upper(joltageValue.add(TOLERANCIA_JOLTAGE));

            for(int buttonIndex = 0; buttonIndex < buttons.size(); buttonIndex++) {
                Button button = buttons.get(buttonIndex);
                if(button.getToggles().contains(joltageIndex)) {
                    Variable variable = createdVariables.get(buttonIndex);
                    linearRestrictionJoltage.set(variable, 1);
                }
            }

            }
        /*
        Expression sumExpression = model.addExpression("TotalPressesSum").upper(19877);
        for (Variable var : createdVariables.values()) {
            sumExpression.set(var, 1);
        }*/

        //model.limitObjective(new BigDecimal(19817), new BigDecimal(19878));
        ExpressionsBasedModel.Description description = model.describe();
        Optimisation.Result modelResult = model.minimise();



        for(int j = 0; j < buttons.size(); j++) {
            result += (long) Math.round(modelResult.get(j).doubleValue());
        }

        // longValue : 19859
        // Math.floor: 19859
        // Math.round: 19878
        // Math.ceil : 19891

        // longValueExact -> error
        return result;
    }


}
