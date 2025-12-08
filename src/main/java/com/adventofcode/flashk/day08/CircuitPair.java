package com.adventofcode.flashk.day08;


import module java.base;
import com.adventofcode.flashk.common.Vector3;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode
public class CircuitPair implements Comparable<CircuitPair> {

    private final JunctionBox first;
    private final JunctionBox second;
    private final double distance;

    public CircuitPair(JunctionBox first, JunctionBox second) {
        this.first = first;
        this.second = second;
        this.distance = Vector3.distance(first.getPosition(), second.getPosition());
    }

    @Override
    public int compareTo(@NotNull CircuitPair o) {
        return Double.compare(distance, o.distance);
    }
}
