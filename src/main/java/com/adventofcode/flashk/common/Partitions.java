package com.adventofcode.flashk.common;

import module java.base;

/// Utility class to make partitions of Strings, Lists etc.
public class Partitions {

    private Partitions() {}

    /// Creates a List of Strings with the given partition size
    ///
    /// Example:
    ///
    /// ```
    /// Partitions.fromString("abcd", 1); // ["a","b","c","d"]
    /// Partitions.fromString("abcd", 2); // ["ab","cd"]
    /// Partitions.fromString("abcd", 3); // ["abc","d"]
    /// ```
    /// @param text the text to create the partition from
    /// @param partitionSize the size of the partition
    /// @return a list containing all the partitions created from the text.
    public static List<String> fromString(String text, int partitionSize) {
        List<String> results = new ArrayList<>();

        int length = text.length();

        for (int i = 0; i < length; i += partitionSize) {
            results.add(text.substring(i, Math.min(length, i + partitionSize)));
        }

        return results;
    }

}
