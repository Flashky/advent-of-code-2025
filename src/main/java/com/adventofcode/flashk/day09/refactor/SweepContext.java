package com.adventofcode.flashk.day09.refactor;

import module java.base;
import com.adventofcode.flashk.day09.Segment1D;

public interface SweepContext {

    void addSegment(Segment1D segment);
    void removeSegment(Segment1D segment);
    void addRectangle(Rectangle rectangle);
    void removeRectangle(Rectangle rectangle);

}
