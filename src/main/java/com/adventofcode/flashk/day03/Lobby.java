package com.adventofcode.flashk.day03;

import module java.base;

public class Lobby {

    private final List<String> banks;

    public Lobby(List<String> inputs) {
        banks = inputs;
    }

    public long solveA() {
       return banks.stream().map(this::largestJoltage).mapToLong(Long::new).sum();
    }

    public long solveB() {
        return 0L;
    }

    private int largestJoltage(String bank) {

        // Search first number
        int number = 9;
        boolean found = false;
        int foundIndex = -1;
        int foundNumber = -1;

        while(!found) {
            int index = bank.indexOf(String.valueOf(number));
            if(index != -1) {
                found = true;
                foundIndex = index;
                foundNumber = number;
            } else {
                number--;
            }
        }

        // Situaciones A - Número no en posición final, lookAhead
        StringBuilder result = new StringBuilder();

        if(foundIndex+1 < bank.length()) {
            String right = bank.substring(foundIndex+1);
            int rightNumber = lookAhead(right, foundNumber);
            result.append(foundNumber).append(rightNumber);
        } else {
            String left = bank.substring(0, foundIndex);
            int leftNumber = lookBehind(left, foundNumber);
            result.append(leftNumber).append(foundNumber);
        }

        return Integer.parseInt(result.toString());
    }

    private int lookBehind(String leftBank, int maxNumber) {
        int number = maxNumber - 1;
        boolean found = false;
        int foundNumber = -1;

        while(!found) {
            int index = leftBank.indexOf(String.valueOf(number));
            if(index != -1) {
                found = true;
                foundNumber = number;
            } else {
                number--;
            }
        }

        return foundNumber;
    }

    private int lookAhead(String rightBank, int maxNumber) {
        int number = maxNumber;
        boolean found = false;
        int foundIndex = -1;
        int foundNumber = -1;

        while(!found) {
            int index = rightBank.indexOf(String.valueOf(number));
            if(index != -1) {
                found = true;
                foundNumber = number;
            } else {
                number--;
            }
        }

        return foundNumber;
    }


}
