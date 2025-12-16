package com.adventofcode.flashk.day09.refactor;

import module java.base;
import com.adventofcode.flashk.day09.EventType1D;
import com.adventofcode.flashk.day09.Segment1D;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class Event1DRefactor implements Comparable<Event1DRefactor> {

    /// The exact coordinate that triggered the event.
    ///
    /// Can be either an `x` or an `y` coordinate.
    private int coordinate;

    /// The segment that triggered the event.
    private Segment1D segment;  // The segment this coordinate event belongs to.
    
    ///  The rectangle that triggered the event.
    private Rectangle rectangle;

    /// The {@link EventType1D type} of event.
    private EventType1D type;

    public Event1DRefactor(int coordinate, Segment1D segment, EventType1D type) {
        this.coordinate = coordinate;
        this.segment = segment;
        this.type = type;
    }

    public Event1DRefactor(int coordinate, Rectangle rectangle, EventType1D type) {
        this.coordinate = coordinate;
        this.rectangle = rectangle;
        this.type = type;
    }

    @Override
    public int compareTo(@NotNull Event1DRefactor other) {

        // Priority on lower coordinate
        if(this.coordinate != other.coordinate) {
            return Integer.compare(this.coordinate, other.coordinate);
        }

        // In case of being same coordinates, give priority depending on the event type
        return Integer.compare(this.type.getPriority(), other.type.getPriority());
    }
}
