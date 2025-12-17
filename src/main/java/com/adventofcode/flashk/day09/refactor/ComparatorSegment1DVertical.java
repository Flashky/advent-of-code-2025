package com.adventofcode.flashk.day09.refactor;

import static java.lang.IO.println;

import module java.base;
import com.adventofcode.flashk.day09.Segment1D;

public class ComparatorSegment1DVertical implements Comparator<Segment1D> {

    @Override
    public int compare(Segment1D o1, Segment1D o2) {

        if(o1.getMinY() != o2.getMinY()) {
            return Integer.compare(o1.getMinY(), o2.getMinY());
        }

        return Integer.compare(o1.getMinX(), o2.getMinX());
    }
}
