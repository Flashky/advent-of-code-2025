package com.adventofcode.flashk.common.test.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import module java.base;
import java.util.stream.Collectors;

/// Helper class for reading Advent of Code input files.
/// All input files are expected to be located under `src/test/resources/inputs` path.
public final class Input {

	private static final String PATH_INPUTS = "src/test/resources/inputs";

	private Input() {}
	
	public static List<Integer> readIntegerLines(String inputFolder, String inputFile) {

		List<Integer> input;
		
		try {
			Path path = Paths.get(PATH_INPUTS, inputFolder, inputFile).toAbsolutePath();
			input = Files.lines(path).map(Integer::parseInt).collect(Collectors.toList());
			
		} catch (IOException e) {
			input = new ArrayList<>();
			e.printStackTrace();
		}
		
		return input;
	}
	
	public static List<String> readStringLines(String inputFolder, String inputFile) {

		List<String> input;
		
		try {
			Path path = Paths.get(PATH_INPUTS, inputFolder, inputFile).toAbsolutePath();
			input = Files.lines(path).collect(Collectors.toList());
			
		} catch (IOException e) {
			input = new ArrayList<>();
			e.printStackTrace();
		}
		
		return input;
	}

	/// Reads an input file into a bidimensional char array.
    /// @param inputFolder the folder where the input file is located.
    /// @param inputFile the file to be read.
    /// @return a bidimensional array of characters.
	public static char[][] read2DCharArray(String inputFolder, String inputFile) {
		List<String> inputs = readStringLines(inputFolder,inputFile);

		int rows = inputs.size();
		int cols = inputs.get(0).length();
		char[][] char2D = new char[rows][cols];

		int i = 0;
		for(String input : inputs) {
			char2D[i++] = input.toCharArray();
		}

		return char2D;
	}

	/// Reads an input file into a bidimensional int array.
    /// @param inputFolder the folder where the input file is located.
    /// @param inputFile the file to be read.
    /// @return a bidimensional array of integers.
	public static int[][] read2DIntArray(String inputFolder, String inputFile) {
		List<String> inputs = readStringLines(inputFolder,inputFile);

		int rows = inputs.size();
		int cols = inputs.get(0).length();
		int[][] char2D = new int[rows][cols];

		int i = 0;
		for(String input : inputs) {
			char2D[i++] = input.chars().map(Character::getNumericValue).toArray();
		}

		return char2D;
	}

}
