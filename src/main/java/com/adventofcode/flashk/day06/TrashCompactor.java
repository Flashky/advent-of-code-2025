package com.adventofcode.flashk.day06;

import module java.base;

public class TrashCompactor {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");
    private static final Pattern OPERATION_PATTERN = Pattern.compile("([+*])");

    private final List<Operation> problems = new ArrayList<>();

    public TrashCompactor(List<String> inputs) {
        // Read first the operations
        String operations = inputs.getLast();
        Matcher matcher = OPERATION_PATTERN.matcher(operations);

        while(matcher.find()) {
            problems.add(new Operation(matcher.group()));
        }

        // Add the numbers
        inputs.removeLast();

        for (String input : inputs) {
            Matcher numberMatcher = NUMBER_PATTERN.matcher(input);
            int problemIndex = 0;
            while(numberMatcher.find()){
                problems.get(problemIndex++).addNumber(Integer.parseInt(numberMatcher.group()));
            }
        }

    }

    public long solveA() {
        return problems.stream().mapToLong(Operation::operate).sum();
    }

}
