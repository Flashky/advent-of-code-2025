package com.adventofcode.flashk.day09.refactor;

import module java.base;
import com.adventofcode.flashk.day09.Segment1D;

public class ComparatorSegment1DHorizontal implements Comparator<Segment1D> {

    @Override
    public int compare(Segment1D o1, Segment1D o2) {

        if(o1.getMinX() != o2.getMinX()) {
            return Integer.compare(o1.getMinX(), o2.getMinX());
        }

        return Integer.compare(o1.getMinY(), o2.getMinY());
    }
}
