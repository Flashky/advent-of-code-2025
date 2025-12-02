package com.adventofcode.flashk.day02;

import module java.base;
import com.adventofcode.flashk.common.Partitions;
import lombok.Getter;

@Getter
public class Range {

    private final long start;
    private final long end;

    public Range(String input) {
        String[] values = input.split("-");
        start = Long.parseLong(values[0]);
        end = Long.parseLong(values[1]);
    }

    public Long calculateInvalidIdSum() {
        return LongStream.rangeClosed(start, end).filter(this::isInvalidId).sum();
    }

    public Long calculateInvalidIdSumMulti() {
        return LongStream.rangeClosed(start, end).filter(this::isInvalidIdMulti).sum();
    }


    private boolean isInvalidId(long number) {
        String numberStr = String.valueOf(number);

        if(numberStr.length() % 2 != 0) {
            return false;
        }

        String leftNumber = numberStr.substring(0,numberStr.length()/2);
        String rightNumber = numberStr.substring(numberStr.length()/2);

        return leftNumber.equals(rightNumber);

    }

    private boolean isInvalidIdMulti(long number) {

        String numberStr = String.valueOf(number);

        // Create partitions with the input number string. The maximum partition size is just half the string length
        int maxPartitionSize = numberStr.length() / 2;

        boolean isInvalid = false;
        int partitionSize = 1;
        while(!isInvalid && partitionSize <= maxPartitionSize) {

            // Create the partitions
            List<String> partitions = Partitions.fromString(numberStr, partitionSize);

            // Verify how many matches are in the partitions
            // If all the partitions contains the same number, the number is invalid.
            String matchNumber = partitions.getFirst();
            long matches = partitions.stream().filter(p -> p.equals(matchNumber)).count();

            if(matches == partitions.size()) {
                isInvalid = true;
            }

            partitionSize++;
        }
        return isInvalid;
    }

}
