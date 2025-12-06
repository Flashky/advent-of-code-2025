package com.adventofcode.flashk.day06;

import module java.base;
import org.apache.commons.lang3.StringUtils;

public class TrashCompactorRTL extends TrashCompactor {

    public TrashCompactorRTL(List<String> inputs) {
        super(inputs);
    }

    @Override
    protected void initializeNumbers(List<String> inputs) {

        // Every input column will have a StringBuilder, including the empty ones
        Map<Integer, StringBuilder> rtlMap = new HashMap<>();

        // Fill the StringBuilders
        for (String input : inputs) {
            char[] characters = input.toCharArray();

            for (int i = 0; i < characters.length; i++) {
                rtlMap.putIfAbsent(i, new StringBuilder());
                rtlMap.get(i).append(characters[i]);
            }
        }

        // Build the numbers from the StringBuilder
        int operationIndex = 0;
        for (StringBuilder sb : rtlMap.values()) {
            String value = sb.toString().replaceAll(StringUtils.SPACE, StringUtils.EMPTY);
            if (StringUtils.isNotBlank(value)) {
                getProblems().get(operationIndex).addNumber(Integer.parseInt(value));
            } else {
                operationIndex++;
            }
        }
    }


}
