package com.adventofcode.flashk.day03;

import module java.base;
import org.apache.commons.lang3.StringUtils;

public class Lobby {

    private final List<String> banks;
    private int maxDigits;

    public Lobby(List<String> inputs) {
        banks = inputs;
    }


    public long solve(int maxDigits) {
        this.maxDigits = maxDigits;
        return banks.stream().map(this::largestJoltageRecursive).mapToLong(Long::new).sum();
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
            int rightNumber = search(right, foundNumber);
            result.append(foundNumber).append(rightNumber);
        } else {
            String left = bank.substring(0, foundIndex);
            int leftNumber = search(left, foundNumber-1);
            result.append(leftNumber).append(foundNumber);
        }

        return Integer.parseInt(result.toString());
    }

    private int search(String bank, int maxNumber) {
        int number = maxNumber;
        boolean found = false;
        int foundNumber = -1;

        while(!found) {
            int index = bank.indexOf(String.valueOf(number));
            if(index != -1) {
                found = true;
                foundNumber = number;
            } else {
                number--;
            }
        }

        return foundNumber;
    }

    private long largestJoltageRecursive(String bank) {
        String result = search(0,bank,9);
        return Long.parseLong(result);
    }

    private String search(int foundDigits, String bank, int maxNumber) {

        if(foundDigits == maxDigits || bank.isBlank()) {
            return StringUtils.EMPTY;
        }

        int number = maxNumber;
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

        StringBuilder result = new StringBuilder();
        String right = bank.substring(foundIndex+1);
        String left = bank.substring(0, foundIndex);
        if(foundIndex+1 < bank.length()) {
            //String right = bank.substring(foundIndex+1);
            String rightNumber = search(foundDigits+1, right, foundNumber);
            result.append(foundNumber).append(rightNumber);
        } else {
            //String left = bank.substring(0, foundIndex);
            String leftNumber = search(foundDigits+1, left, foundNumber-1);
            result.append(leftNumber).append(foundNumber);
        }


        if(foundDigits + result.toString().length() <  maxDigits && !left.isBlank()) {
            foundDigits += result.toString().length();
            // Force search to the left to find missing digits
            String leftNumber = search(foundDigits+1, left, foundNumber-1);
            result.append(leftNumber).append(foundNumber);
        }
        return result.toString();
    }
}
