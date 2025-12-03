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
        String result = search(0,bank);
        return Long.parseLong(result);
    }

    private String search(int foundDigits, String bank) {

        if(foundDigits == maxDigits || bank.isBlank()) {
            return StringUtils.EMPTY;
        }

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

        StringBuilder result = new StringBuilder();
        String right = bank.substring(foundIndex+1);
        String rightNumber = search(foundDigits+1, right);

        String partialResult = foundNumber + rightNumber;

        if(foundDigits + partialResult.length() < maxDigits) {
            // Mirar a la izquierda
            String left = bank.substring(0, foundIndex);
            String leftNumber = search(foundDigits+partialResult.length(), left);
            partialResult = leftNumber + partialResult;
        }

        // Si el partialResult no tiene la longitud adecuada, miramos a la izquierda.

        /*
        String left = bank.substring(0, foundIndex);

        if(foundIndex+1 < bank.length()) {
            //String right = bank.substring(foundIndex+1);
            String rightNumber = search(foundDigits+1, right);
            result.append(foundNumber).append(rightNumber);
        } else {
            //String left = bank.substring(0, foundIndex);
            String leftNumber = search(foundDigits+1, left);
            result.append(leftNumber).append(foundNumber);
        }


        if(foundDigits + result.toString().length() <  maxDigits && !left.isBlank()) {
            foundDigits += result.toString().length();
            // Force search to the left to find missing digits
            String leftNumber = search(foundDigits+1, left);
            result.append(leftNumber).append(foundNumber);
        }

*/

        /*
        String rightNumber = search(foundDigits+1, right);
        long resultRight = Long.parseLong(foundNumber + rightNumber);

        String leftNumber = search(foundDigits+1, left);
        long resultLeft = Long.parseLong(leftNumber + foundNumber);

        if(resultRight > resultLeft) {
            result.append(foundNumber).append(rightNumber);
        } else {
            result.append(leftNumber).append(foundNumber);
        }*/

        return partialResult;
    }
}
