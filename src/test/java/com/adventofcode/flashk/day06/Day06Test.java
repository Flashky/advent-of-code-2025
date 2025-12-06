package com.adventofcode.flashk.day06;

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

@DisplayName(TestDisplayName.DAY_06)
@TestMethodOrder(OrderAnnotation.class)
class Day06Test {

	private static final String INPUT_FOLDER = TestFolder.DAY_06;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	void part1SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

        TrashCompactor trashCompactor = new TrashCompactor(inputs);
        long result = trashCompactor.solveA();

		assertEquals(4277556L, result);
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	void part1InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

        TrashCompactor trashCompactor = new TrashCompactor(inputs);
        long result = trashCompactor.solveA();

		assertEquals(6209956042374L, result);

	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	void part2SampleTest() {

		// Read input file
        List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

        TrashCompactorRTL trashCompactorRTL = new TrashCompactorRTL(inputs);
        long result = trashCompactorRTL.solve();

		assertEquals(3263827L, result);
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	void part2InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

        TrashCompactorRTL trashCompactorRTL = new TrashCompactorRTL(inputs);
        long result = trashCompactorRTL.solve();

		assertEquals(12608160008022L,result);

	}

}
