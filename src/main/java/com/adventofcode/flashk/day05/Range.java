package com.adventofcode.flashk.day05;

import module java.base;
import lombok.Getter;

@Getter
public class Range {

    private long start;
    private long end;
    private boolean valid = true;

    public Range(String input) {
        String[] values = input.split("-");
        start = Long.parseLong(values[0]);
        end = Long.parseLong(values[1]);
    }

    public boolean isInRange(Long value) {
        return value >= start && value <= end;
    }

    public void reduce(Range other) {

        // Cases:
        // 1. The other range partially overlaps current range by the right => update current range end.
        // 2. the other range partially overlaps current range by the left  => update current range start.
        // 3. The other range is totally overlapped by current range => set other range as invalid.
        // 4. Same as 3 but in the opposite direction, not needed to calculate.

        if(other.start <= end && other.end > end) {
            end = other.start - 1;
        } else if(other.end >= start && other.start < start) {
            start = other.end + 1;
        } else if(other.start >= start && other.end <= end) {
            other.valid = false;
        }

    }

    public long getIngredientsCount() {

        if(!valid || start > end){
            return 0;
        }

        return end - start + 1;
    }


}
