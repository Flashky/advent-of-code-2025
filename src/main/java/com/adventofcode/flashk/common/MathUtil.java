package com.adventofcode.flashk.common;

import java.util.function.DoubleFunction;
import java.util.function.IntFunction;
import java.util.function.LongFunction;

public final class MathUtil {

	private MathUtil() {}

	/// Applies the sums of a function over a range of values.
    /// Example:
    /// ```
    /// summation(1, 5, i -> i * i) // returns 55 (1^2 + 2^2 + 3^2 + 4^2 + 5^2)
    /// summation(1, 5, i -> i) // returns 15 (1 + 2 + 3 + 4 + 5)
    /// summation(0, 0, i -> i) // returns 0 (0)
    /// ```
    /// @param min the minimum value to start the summation. Must be smaller or equal to max.
    /// @param max the maximum value to end the summation. Must be greater or equal to min.
    /// @param summationFunction the function to apply to each value in the range before summing them up.
    /// @return the summation of the function applied to each value in the range
    /// @throws IllegalArgumentException if min is greater than max
	public static int summation(int min, int max, IntFunction<Integer> summationFunction) {

		if(min > max) {
			throw new IllegalArgumentException("min must be less than or equal to max");
		}

		int sum = 0;

		for (int i = min; i <= max; i++) {
			sum += summationFunction.apply(i);
		}

		return sum;
	}

    /// Applies the sums of a function over a range of values.
    /// Example:
    /// ```
    /// summation(1, 5, i -> i * i) // returns 55 (1^2 + 2^2 + 3^2 + 4^2 + 5^2)
    /// summation(1, 5, i -> i) // returns 15 (1 + 2 + 3 + 4 + 5)
    /// summation(0, 0, i -> i) // returns 0 (0)
    /// ```
    /// @param min the minimum value to start the summation. Must be smaller or equal to max.
    /// @param max the maximum value to end the summation. Must be greater or equal to min.
    /// @param summationFunction the function to apply to each value in the range before summing them up.
    /// @return the summation of the function applied to each value in the range
    /// @throws IllegalArgumentException if min is greater than max
    public static long summation(long min, long max, LongFunction<Long> summationFunction) {

        if(min > max) {
            throw new IllegalArgumentException("min must be less than or equal to max");
        }

        long sum = 0;

        for (long i = min; i <= max; i++) {
            sum += summationFunction.apply(i);
        }

        return sum;
    }

    /// Applies the sums of a function over a range of values.
    /// Example:
    /// ```
    /// summation(1, 5, i -> i * i) // returns 55 (1^2 + 2^2 + 3^2 + 4^2 + 5^2)
    /// summation(1, 5, i -> i) // returns 15 (1 + 2 + 3 + 4 + 5)
    /// summation(0, 0, i -> i) // returns 0 (0)
    /// ```
    /// @param min the minimum value to start the summation. Must be smaller or equal to max.
    /// @param max the maximum value to end the summation. Must be greater or equal to min.
    /// @param summationFunction the function to apply to each value in the range before summing them up.
    /// @return the summation of the function applied to each value in the range
    /// @throws IllegalArgumentException if min is greater than max
    public static double summation(double min, double max, DoubleFunction<Double> summationFunction) {

        if(min > max) {
            throw new IllegalArgumentException("min must be less than or equal to max");
        }

        double sum = 0;

        for (double i = min; i <= max; i++) {
            sum += summationFunction.apply(i);
        }

        return sum;
    }

	/// Applies the product of a function over a range of values.
    /// Example:
    /// ```
    /// product(1, 5, i -> i * i) // returns 14400 (1^2 * 2^2 * 3^2 * 4^2 * 5^2)
    /// product(1, 5, i -> i) // returns 120 (1 * 2 * 3 * 4 * 5)
    /// product(0, 0, i -> i) // returns 0 (0)
    /// ```
    /// @param min the minimum value to start the summation. Must be smaller or equal to max.
    /// @param max the maximum value to end the summation. Must be greater or equal to min.
    /// @param productFunction the function to apply to each value in the range before summing them up.
    /// @return the summation of the function applied to each value in the range
    /// @throws IllegalArgumentException if min is greater than max
	public static int product(int min, int max, IntFunction<Integer> productFunction) {

		if(min > max) {
			throw new IllegalArgumentException("min must be less than or equal to max");
		}

		int product = 1;

		for (int i = min; i <= max; i++) {
			product *= productFunction.apply(i);
		}

		return product;
	}

	/// Applies the product of a function over a range of values.
    /// Example:
    /// ```
    /// product(1, 5, i -> i * i) // returns 14400 (1^2 * 2^2 * 3^2 * 4^2 * 5^2)
    /// product(1, 5, i -> i) // returns 120 (1 * 2 * 3 * 4 * 5)
    /// product(0, 0, i -> i) // returns 0 (0)
    /// ```
    /// @param min the minimum value to start the summation. Must be smaller or equal to max.
    /// @param max the maximum value to end the summation. Must be greater or equal to min.
    /// @param productFunction the function to apply to each value in the range before summing them up.
    /// @return the summation of the function applied to each value in the range
    /// @throws IllegalArgumentException if min is greater than max
	public static long product(long min, long max, LongFunction<Long> productFunction) {

		if(min > max) {
			throw new IllegalArgumentException("min must be less than or equal to max");
		}

		long product = 1;

		for (long i = min; i <= max; i++) {
			product *= productFunction.apply(i);
		}

		return product;
	}

}
