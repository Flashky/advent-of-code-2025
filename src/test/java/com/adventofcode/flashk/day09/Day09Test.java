package com.adventofcode.flashk.day09;

import module java.base;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.adventofcode.flashk.common.test.constants.TestDisplayName;
import com.adventofcode.flashk.common.test.constants.TestFilename;
import com.adventofcode.flashk.common.test.constants.TestFolder;
import com.adventofcode.flashk.common.test.constants.TestTag;
import com.adventofcode.flashk.common.test.utils.Input;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(TestDisplayName.DAY_09)
@TestMethodOrder(OrderAnnotation.class)
class Day09Test {

	private static final String INPUT_FOLDER = TestFolder.DAY_09;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	void part1SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

		MovieTheater movieTheater = new MovieTheater(inputs);
        long result = movieTheater.solveA();

		assertEquals(50L,result);
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	void part1InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		MovieTheater movieTheater = new MovieTheater(inputs);
        long result = movieTheater.solveA();

		assertEquals(4754955192L,result);

	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	void part2SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

        //MovieTheaterPolygon2 movieTheater = new MovieTheaterPolygon2(inputs);
		MovieTheater movieTheater = new MovieTheater(inputs);
        long result = movieTheater.solveB();

		assertEquals(24L,result);
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	void part2InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		MovieTheater movieTheater = new MovieTheater(inputs);
        long result = movieTheater.solveB();

		assertEquals(1568849600L,result);

	}

}
