package com.adventofcode.flashk.day10;

import module java.base;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.adventofcode.flashk.common.test.constants.TestDisplayName;
import com.adventofcode.flashk.common.test.constants.TestFilename;
import com.adventofcode.flashk.common.test.constants.TestFolder;
import com.adventofcode.flashk.common.test.constants.TestTag;
import com.adventofcode.flashk.common.test.utils.Input;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(TestDisplayName.DAY_10)
@TestMethodOrder(OrderAnnotation.class)
class Day10Test {

	private static final String INPUT_FOLDER = TestFolder.DAY_10;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	void part1SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

        Factory factory = new Factory(inputs);
        long result = factory.solveA();

		assertEquals(7L,result);
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	void part1InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

        Factory factory = new Factory(inputs);
        long result = factory.solveA();

		assertEquals(512L,result);

	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	void part2SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

        Factory factory = new Factory(inputs);
        long result = factory.solveB();

		assertEquals(33L, result);
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	void part2InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

        Factory factory = new Factory(inputs);
        long result = factory.solveB();


		// Apache SimplexSolver
		// longValue:   19817 -> Too low
        // Math.round:  19829 -> No ejecutado, pero too low
		// Math.ceil:   19834 -> Too low
        // Pivot BLAND: 19847 -> That's not the right answer
        // round first: 19854 -> That's not the right answer


		// OjAlgo:
        // weight(1) : 19857


		assertEquals(19857L,result);

	}
}
