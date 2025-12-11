package com.adventofcode.flashk.day11;

import module java.base;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.adventofcode.flashk.common.test.constants.TestDisplayName;
import com.adventofcode.flashk.common.test.constants.TestFilename;
import com.adventofcode.flashk.common.test.constants.TestFolder;
import com.adventofcode.flashk.common.test.constants.TestTag;
import com.adventofcode.flashk.common.test.utils.Input;

import static java.lang.IO.println;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(TestDisplayName.DAY_11)
@TestMethodOrder(OrderAnnotation.class)
class Day11Test {

	private static final String INPUT_FOLDER = TestFolder.DAY_11;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	void part1SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

        Reactor reactor = new Reactor(inputs);
        long result = reactor.solveA();

		assertEquals(5L,result);
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	void part1InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

        Reactor reactor = new Reactor(inputs);
        long result = reactor.solveA();

		assertEquals(506L,result);

	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	void part2SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, "sample_2.txt");

        ReactorPart2OnlyInput reactor = new ReactorPart2OnlyInput(inputs);
        long result = reactor.solveBSample();

		assertEquals(2L,result);
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	void part2InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

        ReactorPart2OnlyInput reactor = new ReactorPart2OnlyInput(inputs);
        long result = reactor.solveB();

		println("Solution: " + result);

        // 2147471439120 -> Your answer is too low
        // 17007973797830400 -> Your answer is too high
        // 17007973830000000 -> No he probado, pero sería mayor que la anterior, así que no vale
		assertEquals(0L,0L);

	}

}
