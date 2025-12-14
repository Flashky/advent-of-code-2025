package com.adventofcode.flashk.day09;

import static java.lang.IO.println;

import module java.base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
public class Event1D implements Comparable<Event1D> {

    /// The exact coordinate that triggered the event.
    ///
    /// Can be either an `x` or an `y` coordinate.
    private int coordinate;

    /// The segment the previous coordinate belongs to.
    private Segment1D segment;  // The segment this coordinate event belongs to.

    /// The {@link EventType1D type} of event.
    private EventType1D type;

    @Override
    public int compareTo(@NotNull Event1D other) {

        // Priority on lower coordinate
        if(this.coordinate != other.coordinate) {
            return Integer.compare(this.coordinate, other.coordinate);
        }

        // In case of being same coordinates, give priority depending on the event type
        return Integer.compare(this.type.getPriority(), other.type.getPriority());
    }
}
