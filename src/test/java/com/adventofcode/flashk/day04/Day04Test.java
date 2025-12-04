package com.adventofcode.flashk.day04;

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

@DisplayName(TestDisplayName.DAY_04)
@TestMethodOrder(OrderAnnotation.class)
class Day04Test  {

	private static final String INPUT_FOLDER = TestFolder.DAY_04;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	void part1SampleTest() {

		// Read input file
        char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

        PrintingDepartment printingDepartment = new PrintingDepartment(inputs);
        long result = printingDepartment.solveA();

		assertEquals(13L,result);
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	void part1InputTest() {

		// Read input file
        char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);

        PrintingDepartment printingDepartment = new PrintingDepartment(inputs);
        long result = printingDepartment.solveA();

		assertEquals(1356L,result);

	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	void part2SampleTest() {

		// Read input file
        char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

        PrintingDepartment printingDepartment = new PrintingDepartment(inputs);
        long result = printingDepartment.solveB();

		assertEquals(43L,result);
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	void part2InputTest() {

		// Read input file
        char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);

        PrintingDepartment printingDepartment = new PrintingDepartment(inputs);
        long result = printingDepartment.solveB();

		assertEquals(8713L,result);

	}

}
