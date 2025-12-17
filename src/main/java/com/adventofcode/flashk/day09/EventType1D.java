package com.adventofcode.flashk.day09;

import module java.base;
import lombok.Getter;

public enum EventType1D {

    START(1),
    START_RECTANGLE(2),
    END_RECTANGLE(3),
    END(4);

    @Getter
    private final int priority;

    EventType1D(int priority) {
        this.priority = priority;
    }
}
