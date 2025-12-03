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
        return banks.stream().mapToLong(this::search).sum();

    }

    private long search(String bank) {
        String result = search(0, bank);
        return Long.parseLong(result);
    }

    private String search(int foundDigits, String bank) {

        if(foundDigits == maxDigits || bank.isBlank()) {
            return StringUtils.EMPTY;
        }

        // Find the first highest number and its index
        int number = 9;
        int foundIndex = -1;
        int foundNumber = -1;

        while(foundIndex == -1) {
            int index = bank.indexOf(String.valueOf(number));
            if(index != -1) {
                foundIndex = index;
                foundNumber = number;
            }
            number--;
        }

        // Search digits at the right of current item
        String right = bank.substring(foundIndex+1);
        String rightNumber = search(foundDigits+1, right);

        String partialResult = foundNumber + rightNumber;

        // When there are not enough digits at the right, search them at the left.
        if(foundDigits + partialResult.length() < maxDigits) {
            String left = bank.substring(0, foundIndex);
            String leftNumber = search(foundDigits+partialResult.length(), left);
            partialResult = leftNumber + partialResult;
        }

        return partialResult;
    }
}
