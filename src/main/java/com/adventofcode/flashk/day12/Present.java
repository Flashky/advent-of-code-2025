package com.adventofcode.flashk.day12;

import module java.base;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Present {

    private final short index;
    private final int size;

    public Present(List<String> input) {
        index = Short.parseShort(input.getFirst().replace(":", StringUtils.EMPTY));
        size = input.stream()
                    .skip(1)
                    .limit(3)
                    .map(str -> StringUtils.countMatches(str, "#"))
                    .mapToInt(Integer::intValue)
                    .sum();
    }


}
