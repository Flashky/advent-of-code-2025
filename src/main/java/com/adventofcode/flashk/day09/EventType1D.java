package com.adventofcode.flashk.day09;

import module java.base;
import lombok.Getter;

public enum EventType1D {

    START(1),
    START_SQ_LOWER(2),
    START_SQ_HIGHER(3),
    END_SQ_HIGHER(4),
    END_SQ_LOWER(5),
    END(6);

    @Getter
    private final int priority;

    EventType1D(int priority) {
        this.priority = priority;
    }
}
