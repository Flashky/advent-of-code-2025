package com.adventofcode.flashk.day08;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(TestDisplayName.DAY_08)
@TestMethodOrder(OrderAnnotation.class)
class Day08Test {

	private static final String INPUT_FOLDER = TestFolder.DAY_08;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	void part1SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

        Playground playground = new Playground(inputs);
        long result = playground.solveA(10);

		assertEquals(40L,result);
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	void part1InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
        Playground playground = new Playground(inputs);
        long result = playground.solveA(1000);

		assertEquals(175440L,result);

	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	void part2SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

        Playground playground = new Playground(inputs);
        long result = playground.solveB();

		assertEquals(25272L,result);
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	void part2InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

        Playground playground = new Playground(inputs);
        long result = playground.solveB();

		assertEquals(3200955921L,result);

	}

    @Test
    @Order(7)
    @Tag(TestTag.PART_2)
    @Tag(TestTag.SAMPLE)
    @DisplayName(TestDisplayName.PART_2_SAMPLE + " (MST)")
    void part2SampleWithMSTTest() {

        // Read input file
        List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.SAMPLE_FILE);

        PlaygroundMST playground = new PlaygroundMST(inputs);
        long result = playground.solveB();

        assertEquals(25272L,result);
    }

    @Test
    @Order(8)
    @Tag(TestTag.PART_2)
    @Tag(TestTag.INPUT)
    @DisplayName(TestDisplayName.PART_2_INPUT + " (MST)")
    void part2InputWithMSTTest() {

        // Read input file
        List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

        PlaygroundMST playground = new PlaygroundMST(inputs);
        long result = playground.solveB();

        assertEquals(3200955921L,result);

    }

}
