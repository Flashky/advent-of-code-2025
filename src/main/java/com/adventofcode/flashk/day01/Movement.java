package com.adventofcode.flashk.day01;

import static java.lang.IO.println;

import module java.base;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.analysis.function.Max;

@Getter
@Setter
public class Movement {

    private static final int MIN = 0;
    private static final int MAX = 99;

    private short direction;
    private int amount;
    private int clicks;

    public Movement(String instruction) {
        direction = (instruction.startsWith("R")) ? (short) 1 : -1;
        amount = Integer.parseInt(instruction.substring(1));
    }

    public int move(int position) {

        int newPosition = position;
        clicks = 0;

        for(int i = 0; i < amount; i++) {
            newPosition += direction;

            if(newPosition < MIN) {
                newPosition = MAX;
            } else if(newPosition > MAX) {
                newPosition = MIN;
            }

            if(newPosition == MIN) {
                clicks++;
            }
        }

        return newPosition;

    }

}
