package com.adventofcode.flashk.day01;

import module java.base;

public class SecretEntrance {

    private final List<Movement> movements;

    public SecretEntrance(List<String> inputs) {
        movements = inputs.stream().map(Movement::new).toList();
    }

    public long solveA() {

        int position = 50;
        long times = 0;

        for(Movement movement : movements) {
            position = movement.move(position);
            if(position == 0) {
                times++;
            }

        }
        return times;
    }

    public long solveB() {

        int position = 50;
        long times = 0;

        for(Movement movement : movements) {
            position = movement.move(position);
            times += movement.getClicks();
        }

        return times;
    }
}
