package com.adventofcode.flashk.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class Vector2Test {

	// Constructors tests

	@Test
	void testVector2() {
		Vector2 result = new Vector2();

		assertEquals(0, result.getX());
		assertEquals(0, result.getY());
	}

	@Test
	void testVector2Vector2() {
		Vector2 expected = new Vector2(2,3);
		Vector2 result = new Vector2(expected);

		assertEquals(expected, result);
		assertNotSame(expected, result);
	}

	@Test
	void testVector2String() {
		Vector2 result = new Vector2("2,3");

		assertEquals(2, result.getX());
		assertEquals(3, result.getY());
	}

	@Test
	void testVector2StringZero() {
		Vector2 result = new Vector2("0,0");

		assertEquals(0, result.getX());
		assertEquals(0, result.getY());
	}

	@Test
	void testVector2StringNegatives() {
		Vector2 result = new Vector2("-20,-367");

		assertEquals(-20, result.getX());
		assertEquals(-367, result.getY());
	}

	// Unitary vectors creation tests

	@Test
	void testRight(){

		Vector2 vector = Vector2.right();

		assertEquals(1, vector.getX());
		assertEquals(0, vector.getY());
	}

	@Test
	void testLeft(){

		Vector2 vector = Vector2.left();

		assertEquals(-1, vector.getX());
		assertEquals(0, vector.getY());
	}

	@Test
	void testUp(){

		Vector2 vector = Vector2.up();

		assertEquals(0, vector.getX());
		assertEquals(1, vector.getY());
	}

	@Test
	void testDown(){

		Vector2 vector = Vector2.down();

		assertEquals(0, vector.getX());
		assertEquals(-1, vector.getY());
	}

	@Test
	void testUpRight(){

		Vector2 vector = Vector2.upRight();

		assertEquals(1, vector.getX());
		assertEquals(1, vector.getY());
	}

	@Test
	void testUpLeft(){

		Vector2 vector = Vector2.upLeft();

		assertEquals(-1, vector.getX());
		assertEquals(1, vector.getY());
	}

	@Test
	void testUpDownRight(){

		Vector2 vector = Vector2.downRight();

		assertEquals(1, vector.getX());
		assertEquals(-1, vector.getY());
	}

	@Test
	void testUpDownLeft(){

		Vector2 vector = Vector2.downLeft();

		assertEquals(-1, vector.getX());
		assertEquals(-1, vector.getY());
	}

	// Transform tests

	@Test
	void testDistance() {
		Vector2 v1 = new Vector2(2,3);
		Vector2 v2 = new Vector2(3,5);

		double value = Vector2.distance(v1, v2);

		assertEquals(2.23606797749979, value);
	}

	@Test
	void testManhattanDistance() {
		Vector2 v1 = new Vector2(2, 3);
		Vector2 v2 = new Vector2(3, 5);

		int value = Vector2.manhattanDistance(v1, v2);

		assertEquals(3, value);
	}

	@Test
	void testManhattanDistanceNegativeToPositive() {
		Vector2 v1 = new Vector2(-1, -1);
		Vector2 v2 = new Vector2(1, 1);

		int value = Vector2.manhattanDistance(v1, v2);

		assertEquals(4, value);
	}

	@Test
	void testManhattanDistancePositiveToNegative() {
		Vector2 v1 = new Vector2(1, 1);
		Vector2 v2 = new Vector2(-1, -1);

		int value = Vector2.manhattanDistance(v1, v2);

		assertEquals(4, value);
	}

	@Test
	void testTransformScalar() {
		Vector2 startPos = new Vector2(2,5);
		startPos.transform(3);

		assertEquals(5, startPos.getX());
		assertEquals(8, startPos.getY());
	}

	@Test
	void testTransformX() {
		Vector2 startPos = new Vector2(2, 5);
		startPos.transformX(3);

		assertEquals(5, startPos.getX());
	}

	@Test
	void testTransformY() {
		Vector2 startPos = new Vector2(2, 5);
		startPos.transformY(3);

		assertEquals(8, startPos.getY());
	}

	@Test
	void testTransformStatic() {
		Vector2 startPos = new Vector2(2,5);
		Vector2 endPos = new Vector2(3,7);
		Vector2 result = Vector2.transform(startPos, endPos);

		assertEquals(5, result.getX());
		assertEquals(12, result.getY());
	}

	@Test
	void testTransform() {

		Vector2 startPos = new Vector2(2,5);
		Vector2 endPos = new Vector2(3,7);

		startPos.transform(endPos);

		assertEquals(5, startPos.getX());
		assertEquals(12, startPos.getY());

	}

	@Test
	void testSubstractAbsY1PositiveStatic() {

		Vector2 leftOperand = new Vector2(0,14);
		Vector2 rightOperand = new Vector2(0,7);

		Vector2 result = Vector2.substractAbs(leftOperand, rightOperand);

		assertNotNull(result);
		assertEquals(0, result.getX());
		assertEquals(7, result.getY());
	}

	@Test
	void testSubstractAbsYNegativeStatic() {

		Vector2 leftOperand = new Vector2(0,7);
		Vector2 rightOperand = new Vector2(0,14);

		Vector2 result = Vector2.substractAbs(leftOperand, rightOperand);

		assertNotNull(result);
		assertEquals(0, result.getX());
		assertEquals(7, result.getY());
	}

	@Test
	void testSubstractAbsXPositiveStatic() {

		Vector2 leftOperand = new Vector2(11,0);
		Vector2 rightOperand = new Vector2(5,0);

		Vector2 result = Vector2.substractAbs(leftOperand, rightOperand);

		assertNotNull(result);
		assertEquals(6, result.getX());
		assertEquals(0, result.getY());
	}

	@Test
	void testSubstractAbsXNegativeStatic() {

		Vector2 leftOperand = new Vector2(5,0);
		Vector2 rightOperand = new Vector2(11,0);

		Vector2 result = Vector2.substractAbs(leftOperand, rightOperand);

		assertNotNull(result);
		assertEquals(6, result.getX());
		assertEquals(0, result.getY());
	}

	@Test
	void testSubstractAbsY1Positive() {

		Vector2 leftOperand = new Vector2(0,14);
		Vector2 rightOperand = new Vector2(0,7);

		leftOperand.substractAbs(rightOperand);

		assertEquals(0, leftOperand.getX());
		assertEquals(7, leftOperand.getY());
	}

	@Test
	void testSubstractAbsYNegative() {

		Vector2 leftOperand = new Vector2(0,7);
		Vector2 rightOperand = new Vector2(0,14);

		leftOperand.substractAbs(rightOperand);

		assertEquals(0, leftOperand.getX());
		assertEquals(7, leftOperand.getY());
	}

	@Test
	void testSubstractAbsXPositive() {

		Vector2 leftOperand = new Vector2(11,0);
		Vector2 rightOperand = new Vector2(5,0);

		leftOperand.substractAbs(rightOperand);

		assertEquals(6, leftOperand.getX());
		assertEquals(0, leftOperand.getY());
	}

	@Test
	void testSubstractAbsXNegative() {

		Vector2 leftOperand = new Vector2(5,0);
		Vector2 rightOperand = new Vector2(11,0);

		leftOperand.substractAbs(rightOperand);

		assertNotNull(leftOperand);
		assertEquals(6, leftOperand.getX());
		assertEquals(0, leftOperand.getY());
	}

	@Test
	void testSubstractStatic() {
		Vector2 leftOperand = new Vector2(5,0);
		Vector2 rightOperand = new Vector2(11,0);

		Vector2 result = Vector2.substract(leftOperand, rightOperand);

		assertNotNull(result);
		assertEquals(-6, result.getX());
		assertEquals(0, result.getY());

		// left and right operands are not modified
		assertEquals(5, leftOperand.getX());
		assertEquals(0, leftOperand.getY());
		assertEquals(11, rightOperand.getX());
		assertEquals(0, rightOperand.getY());
	}

	@Test
	void testDirection() {
		Vector2 p = new Vector2(5,0);
		Vector2 q = new Vector2(11,0);

		Vector2 result = Vector2.direction(p, q);

		assertNotNull(result);
		assertEquals(-6, result.getX());
		assertEquals(0, result.getY());

		// left and right operands are not modified
		assertEquals(5, p.getX());
		assertEquals(0, p.getY());
		assertEquals(11, q.getX());
		assertEquals(0, q.getY());
	}

	@Test
	void testDirectionOpposite() {

		Vector2 p = new Vector2(11,0);
		Vector2 q = new Vector2(5,0);

		Vector2 result = Vector2.direction(p, q);

		assertNotNull(result);
		assertEquals(6, result.getX());
		assertEquals(0, result.getY());

		// left and right operands are not modified
		assertEquals(11, p.getX());
		assertEquals(0, p.getY());
		assertEquals(5, q.getX());
		assertEquals(0, q.getY());
	}

	@Test
	void testHashCodeAndEquals() {
		EqualsVerifier.simple()
				.forClass(Vector2.class)
				.verify();
	}

	// Rotation tests

	@Test
	void testRotateRight() {

		Vector2 startVector = new Vector2(-1,0);
		startVector.rotateRight();

		assertEquals(0, startVector.getX());
		assertEquals(1, startVector.getY());

		startVector.rotateRight();

		assertEquals(1, startVector.getX());
		assertEquals(0, startVector.getY());

		startVector.rotateRight();

		assertEquals(0, startVector.getX());
		assertEquals(-1, startVector.getY());

		startVector.rotateRight();

		assertEquals(-1, startVector.getX());
		assertEquals(0, startVector.getY());

	}

	@Test
	void testRotateRightNonZero() {

		Vector2 startVector = new Vector2(2,1);
		startVector.rotateRight();

		assertEquals(1, startVector.getX());
		assertEquals(-2, startVector.getY());

		startVector.rotateRight();

		assertEquals(-2, startVector.getX());
		assertEquals(-1, startVector.getY());

		startVector.rotateRight();

		assertEquals(-1, startVector.getX());
		assertEquals(2, startVector.getY());

		startVector.rotateRight();

		assertEquals(2, startVector.getX());
		assertEquals(1, startVector.getY());

	}

	@Test
	void testRotateRightNonZeroEqual() {

		Vector2 startVector = new Vector2(5,5);
		startVector.rotateRight();

		assertEquals(5, startVector.getX());
		assertEquals(-5, startVector.getY());

		startVector.rotateRight();

		assertEquals(-5, startVector.getX());
		assertEquals(-5, startVector.getY());

		startVector.rotateRight();

		assertEquals(-5, startVector.getX());
		assertEquals(5, startVector.getY());

		startVector.rotateRight();

		assertEquals(5, startVector.getX());
		assertEquals(5, startVector.getY());

	}

	@Test
	void testRotateLeft() {

		Vector2 startVector = new Vector2(-1,0);
		startVector.rotateLeft();

		assertEquals(0, startVector.getX());
		assertEquals(-1, startVector.getY());

		startVector.rotateLeft();

		assertEquals(1, startVector.getX());
		assertEquals(0, startVector.getY());

		startVector.rotateLeft();

		assertEquals(0, startVector.getX());
		assertEquals(1, startVector.getY());

		startVector.rotateLeft();

		assertEquals(-1, startVector.getX());
		assertEquals(0, startVector.getY());

	}

	@Test
	void testRotateLeftNonZero() {

		Vector2 startVector = new Vector2(2,1);
		startVector.rotateLeft();

		assertEquals(-1, startVector.getX());
		assertEquals(2, startVector.getY());

		startVector.rotateLeft();

		assertEquals(-2, startVector.getX());
		assertEquals(-1, startVector.getY());

		startVector.rotateLeft();

		assertEquals(1, startVector.getX());
		assertEquals(-2, startVector.getY());

		startVector.rotateLeft();

		assertEquals(2, startVector.getX());
		assertEquals(1, startVector.getY());

	}

	@Test
	void testRotateLeftNonZeroEqual() {

		Vector2 startVector = new Vector2(5,5);
		startVector.rotateLeft();

		assertEquals(-5, startVector.getX());
		assertEquals(5, startVector.getY());

		startVector.rotateLeft();

		assertEquals(-5, startVector.getX());
		assertEquals(-5, startVector.getY());

		startVector.rotateLeft();

		assertEquals(5, startVector.getX());
		assertEquals(-5, startVector.getY());

		startVector.rotateLeft();

		assertEquals(5, startVector.getX());
		assertEquals(5, startVector.getY());

	}

	// Normalization tests - normalize()

	@Test
	void testNormalizeLeft() {

		Vector2 vector = new Vector2(-5,0);
		vector.normalize();

		assertEquals(-1,  vector.getX());
		assertEquals(0, vector.getY());
	}

	@Test
	void testNormalizeRight() {

		Vector2 vector = new Vector2(5,0);
		vector.normalize();

		assertEquals(1,  vector.getX());
		assertEquals(0, vector.getY());
	}

	@Test
	void testNormalizeUp() {

		Vector2 vector = new Vector2(0,5);
		vector.normalize();

		assertEquals(0,  vector.getX());
		assertEquals(1, vector.getY());
	}

	@Test
	void testNormalizeDown() {

		Vector2 vector = new Vector2(0,-5);
		vector.normalize();

		assertEquals(0,  vector.getX());
		assertEquals(-1, vector.getY());
	}

	@Test
	void testNormalizeUpRight() {

		Vector2 vector = new Vector2(5,5);
		vector.normalize();

		assertEquals(1,  vector.getX());
		assertEquals(1, vector.getY());
	}

	@Test
	void testNormalizeDownRight() {

		Vector2 vector = new Vector2(5,-5);
		vector.normalize();

		assertEquals(1,  vector.getX());
		assertEquals(-1, vector.getY());
	}

	@Test
	void testNormalizeUpLeft() {

		Vector2 vector = new Vector2(-5,5);
		vector.normalize();

		assertEquals(-1,  vector.getX());
		assertEquals(1, vector.getY());
	}

	@Test
	void testNormalizeDownLeft() {

		Vector2 vector = new Vector2(-5,-5);
		vector.normalize();

		assertEquals(-1,  vector.getX());
		assertEquals(-1, vector.getY());
	}

	@Test
	void testNormalizeOrigin() {

		Vector2 vector = new Vector2(0,0);

		assertThrows(IllegalStateException.class, vector::normalize);

	}


	// Normalization tests - normalized()

	@Test
	void testNormalizedLeft() {

		Vector2 vector = new Vector2(-5,0);
		Vector2 anotherVector = vector.normalized();

		assertEquals(-1,  anotherVector.getX());
		assertEquals(0, anotherVector.getY());
		assertEquals(-5, vector.getX());
		assertEquals(0, vector.getY());

		assertNotSame(anotherVector, vector);
	}

	@Test
	void testNormalizedRight() {

		Vector2 vector = new Vector2(5,0);
		Vector2 anotherVector = vector.normalized();

		assertEquals(1,  anotherVector.getX());
		assertEquals(0, anotherVector.getY());
		assertEquals(5,  vector.getX());
		assertEquals(0, vector.getY());

		assertNotSame(anotherVector, vector);
	}

	@Test
	void testNormalizedUp() {

		Vector2 vector = new Vector2(0,5);
		Vector2 anotherVector = vector.normalized();

		assertEquals(0,  anotherVector.getX());
		assertEquals(1, anotherVector.getY());
		assertEquals(0,  vector.getX());
		assertEquals(5, vector.getY());

		assertNotSame(anotherVector, vector);
	}

	@Test
	void testNormalizedDown() {

		Vector2 vector = new Vector2(0,-5);
		Vector2 anotherVector = vector.normalized();

		assertEquals(0,  anotherVector.getX());
		assertEquals(-1, anotherVector.getY());
		assertEquals(0,  vector.getX());
		assertEquals(-5, vector.getY());

		assertNotSame(anotherVector, vector);
	}

	@Test
	void testNormalizedUpRight() {

		Vector2 vector = new Vector2(5,5);
		Vector2 anotherVector = vector.normalized();

		assertEquals(1,  anotherVector.getX());
		assertEquals(1, anotherVector.getY());
		assertEquals(5,  vector.getX());
		assertEquals(5, vector.getY());

		assertNotSame(anotherVector, vector);
	}

	@Test
	void testNormalizedDownRight() {

		Vector2 vector = new Vector2(5,-5);
		Vector2 anotherVector = vector.normalized();

		assertEquals(1,  anotherVector.getX());
		assertEquals(-1, anotherVector.getY());
		assertEquals(5,  vector.getX());
		assertEquals(-5, vector.getY());

		assertNotSame(anotherVector, vector);
	}

	@Test
	void testNormalizedUpLeft() {

		Vector2 vector = new Vector2(-5,5);
		Vector2 anotherVector = vector.normalized();

		assertEquals(-1,  anotherVector.getX());
		assertEquals(1, anotherVector.getY());
		assertEquals(-5,  vector.getX());
		assertEquals(5, vector.getY());

		assertNotSame(anotherVector, vector);
	}

	@Test
	void testNormalizedDownLeft() {

		Vector2 vector = new Vector2(-5,-5);
		Vector2 anotherVector = vector.normalized();

		assertEquals(-1,  anotherVector.getX());
		assertEquals(-1, anotherVector.getY());
		assertEquals(-5,  vector.getX());
		assertEquals(-5, vector.getY());

		assertNotSame(anotherVector, vector);
	}

	@Test
	void testNormalizedOrigin() {

		Vector2 vector = new Vector2(0,0);

		assertThrows(IllegalStateException.class, vector::normalized);

	}

	@Test
	void multiply() {

		Vector2 vector = new Vector2(2, 3);
		Vector2 result = Vector2.multiply(vector, 5);

		// The result is a new vector with the product of the vector by the scalar
		assertNotSame(vector, result);
		assertEquals(10, result.getX());
		assertEquals(15, result.getY());

		// Original vector is not modified
		assertEquals(2, vector.getX());
		assertEquals(3, vector.getY());
	}
}
