package com.adventofcode.flashk.day06;

import module java.base;
import lombok.Getter;

public abstract class TrashCompactor {

    private static final Pattern OPERATION_PATTERN = Pattern.compile("([+*])");

    @Getter
    private final List<Operation> problems = new ArrayList<>();

    public TrashCompactor(List<String> inputs) {

        // Read the operations
        String operations = inputs.getLast();
        Matcher matcher = OPERATION_PATTERN.matcher(operations);

        while(matcher.find()) {
            problems.add(new Operation(matcher.group()));
        }

        inputs.removeLast();
        initializeNumbers(inputs);
    }

    protected abstract void initializeNumbers(List<String> inputs);

    public long solve() {
        return problems.stream().mapToLong(Operation::operate).sum();
    }
}
