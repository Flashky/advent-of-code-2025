package com.adventofcode.flashk.day10;

import static java.lang.IO.println;

import module java.base;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Combinations;

public class Machine {

    private static final char ON = '#';
    private static final Pattern JOLTAGE_PATTERN = Pattern.compile("\\{([^)]+)}");

    private final boolean[] expectedLights;
    private final List<Button> buttons = new ArrayList<>();
    private final List<Integer> joltages = new ArrayList<>();
    private final int[] expectedJoltages;
    private int joltageNumber;

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
                joltageNumber = Integer.parseInt(group.replace(",", StringUtils.EMPTY));
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
        long result = Long.MAX_VALUE;

        Set<Set<Button>> buttonCombinations = getCombinations();
/*
        for(Set<Button> buttonCombination : buttonCombinations) {
            // Solve Linear Algebra
            long equationResult = solveEquations(buttonCombination);
            if(equationResult < result) {
                result = equationResult;
            }
        }
*/
        return result;
    }

    private long solveEquations(Set<Button> buttonCombination) {
        String modelName = "my_model";
        String solverId = "CBC_MIXED_INTEGER_PROGRAMMING";
        MPSolver solver = MPSolver.createSolver(solverId);

        //MPVariable x = solver.makeIntVar(0, Double.POSITIVE_INFINITY, "x");
        //MPVariable x = solver.makeIntVar(0, Double.POSITIVE_INFINITY, "x");

        MPVariable variables[] = solver.makeIntVarArray(buttonCombination.size(), 0,Double.POSITIVE_INFINITY);

        // Constraints (constantes)

        MPConstraint c1 = solver.makeConstraint(joltageNumber, joltageNumber, "my_equation");

        Map<Button, MPVariable> variablePerButton = new HashMap<>();
        int buttonIndex = 0;
        for(Button button : buttonCombination) {
            c1.setCoefficient(variables[buttonIndex], button.getMultiplier());
            variablePerButton.put(button,variables[buttonIndex]);
        }

        // Función objetivo
        MPObjective objective = solver.objective();
        for(MPVariable variable : variables ){
            objective.setCoefficient(variable, 1);
        }

        final MPSolver.ResultStatus resultStatus = solver.solve();

        if (resultStatus != MPSolver.ResultStatus.OPTIMAL) {
            // There is a solution
            return Long.MAX_VALUE;
        }

        long result = 0;
        for(Button button : variablePerButton.keySet()) {
            result += (long) (button.getMultiplier() * variablePerButton.get(button).solutionValue());
        }

        return result;
    }
    /*
    private long solveEquations(Set<Button> buttonCombination) {

        // Obtain coefficients. Each button has a coefficient
        double[] coefficients1 = new double[buttonCombination.size()];

        int buttonIndex = 0;
        for(Button button : buttonCombination) {
            coefficients1[buttonIndex++] = button.getMultiplier();
        }

        RealMatrix coefficients = MatrixUtils.createRealMatrix(new double[][] { coefficients1 });

        // Obtain constants
        double[] joltageNumbers = { joltageNumber };

        RealVector constants = new ArrayRealVector(joltageNumbers, false);

        // Obtain solver
        // SVD (SingularValueDecomposition (SVD) is designed for matrixes of any size
        DecompositionSolver solver = new SingularValueDecomposition(coefficients).getSolver();

        // Finally, solve by constants
        RealVector solution = solver.solve(constants);

        if(solution.isNaN()) {
            return Long.MAX_VALUE;
        }

        long result = 0;
        for(int i = 0; i < buttonCombination.size(); i++) {
            int roundedResult = (int) Math.ceil(solution.getEntry(i));
            result += roundedResult;
        }

        return result;

    }*/

    /*
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
*/
    /*
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

     */


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

    private Set<Set<Button>> getCombinations() {
        Set<Set<Button>> result = new HashSet<>();

        for(int buttonsNumber = 1; buttonsNumber <= buttons.size(); buttonsNumber++) {
            Combinations combinations = new Combinations(buttons.size(), buttonsNumber);
            Iterator<int[]> iterator = combinations.iterator();
            while(iterator.hasNext()) {
                Set<Button> combination = new HashSet<>();
                int[] buttonIndexes = iterator.next();
                for(int buttonIndex : buttonIndexes) {
                    combination.add(buttons.get(buttonIndex));
                }
                result.add(combination);
            }
        }

        return result;

    }
    private Set<List<Button>> getButtonCombinations() {

        Set<List<Button>> combinations = new HashSet<>();

        for(int buttonsNumber = 1; buttonsNumber <= buttons.size(); buttonsNumber++) {
            combinations.addAll(buttons.stream()
                    .gather(Gatherers.windowSliding(buttonsNumber))
                    .collect(Collectors.toSet()));
        }

        return combinations;
    }


}
