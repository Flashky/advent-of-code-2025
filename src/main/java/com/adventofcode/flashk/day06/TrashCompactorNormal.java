package com.adventofcode.flashk.day06;

import module java.base;

public class TrashCompactorNormal extends TrashCompactor {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");

    public TrashCompactorNormal(List<String> inputs) {
        super(inputs);
    }

    @Override
    protected void initializeNumbers(List<String> inputs) {
        // Initialize the numbers
        for (String input : inputs) {
            Matcher numberMatcher = NUMBER_PATTERN.matcher(input);
            int problemIndex = 0;
            while(numberMatcher.find()){
                getProblems().get(problemIndex++).addNumber(Integer.parseInt(numberMatcher.group()));
            }
        }
    }
}
