package com.adventofcode.flashk.day06;

import module java.base;
import org.apache.commons.lang3.StringUtils;

public class TrashCompactorRTL {

    private static final Pattern OPERATION_PATTERN = Pattern.compile("([+*])");

    private final List<Operation> problems = new ArrayList<>();

    public TrashCompactorRTL(List<String> inputs) {

        // Read the operations
        String operations = inputs.getLast();
        Matcher matcher = OPERATION_PATTERN.matcher(operations);

        while(matcher.find()) {
            for(int i = 1; i <= matcher.groupCount(); i++) {
                problems.add(new Operation(matcher.group(i)));
            }
        }


        initializeNumbers(inputs);

    }

    private void initializeNumbers(List<String> inputs) {

        inputs.removeLast();

        Map<Integer,StringBuilder> rtlMap = new HashMap<>();

        for (String input : inputs) {
            char[] characters = input.toCharArray();
            for (int i = 0; i < characters.length; i++) {
                if (!rtlMap.containsKey(i)) {
                    rtlMap.put(i, new StringBuilder());
                }
                rtlMap.get(i).append(characters[i]);
            }
        }

        int operationIndex = 0;
        for(StringBuilder sb : rtlMap.values()) {
            String value = sb.toString().replaceAll(StringUtils.SPACE, StringUtils.EMPTY);
            if(StringUtils.isNotBlank(value)) {
                problems.get(operationIndex).addNumber(Integer.parseInt(value));
            } else {
                operationIndex++;
            }
        }
    }

    public long solve() {
        return problems.stream().mapToLong(Operation::operate).sum();
    }
}
