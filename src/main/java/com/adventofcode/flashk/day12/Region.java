package com.adventofcode.flashk.day12;

import module java.base;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Region {

    private static final Pattern SIZE_PATTERN = Pattern.compile("(\\d+)x(\\d+):");

    private final int size;
    private final int totalPresentsSizes;

    public Region(String input, List<Present> presents) {
        size = calculateSize(input);
        totalPresentsSizes = calculatePresentSizes(input, presents);
    }

    private static int calculateSize(String input) {
        Matcher sizeMatcher = SIZE_PATTERN.matcher(input);

        if(sizeMatcher.find()) {
            short cols = Short.parseShort(sizeMatcher.group(1));
            short rows = Short.parseShort(sizeMatcher.group(2));
            return cols*rows;
        } else {
            throw new IllegalArgumentException("Invalid input. Must contain dimensions in 'MxN' format.");
        }
    }

    private int calculatePresentSizes(String input, List<Present> presentSizes) {
        Map<Short,Short> requiredPresents = calculateRequiredPresents(input);

        int totalPresentsSize = 0;
        for(Present presentSize : presentSizes) {
            totalPresentsSize += requiredPresents.get(presentSize.getIndex()) * presentSize.getSize();
        }

        return totalPresentsSize;
    }

    private Map<Short, Short> calculateRequiredPresents(String input) {

        Map<Short,Short> requiredPresents = new HashMap<>();
        String[] splittedInput = input.split(":");

        String[] indexes = splittedInput[1].split(StringUtils.SPACE);
        short i = 0;
        for(String index : indexes) {
            if(!StringUtils.EMPTY.equals(index)) {
                requiredPresents.put(i++, Short.parseShort(index));
            }
        }

        return requiredPresents;
    }

    public boolean canFit() {
        return totalPresentsSizes <= size;
    }

}
