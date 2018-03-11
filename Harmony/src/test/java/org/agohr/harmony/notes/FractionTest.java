package org.agohr.harmony.notes;

import org.junit.Test;

import static org.junit.Assert.*;

public class FractionTest {

	@Test
	public void getUpperLower() {
		Fraction fraction = new Fraction(3, 8);
		assertEquals(fraction.getUpper(),3);
		assertEquals(fraction.getLower(), 8);
	}

	@Test
	public void lessOrEqual() {
		assertTrue(new Fraction(1,2).lessOrEqual(new Fraction(1,1)));
		assertTrue(new Fraction(1,2).lessOrEqual(new Fraction(1,2)));
		assertFalse(new Fraction(1,2).lessOrEqual(new Fraction(1,4)));
	}

	@Test
	public void less() {
		assertTrue(new Fraction(1,2).less(new Fraction(1,1)));
		assertFalse(new Fraction(1,2).less(new Fraction(1,2)));
		assertFalse(new Fraction(1,2).less(new Fraction(1,4)));
	}

	@Test
	public void multiply() {
		Fraction f1 = new Fraction(1, 2).multiply(2);
		assertEquals(f1, new Fraction(1,1));
		Fraction f2 = new Fraction(1, 2).multiply(new Fraction(1,2));
		assertEquals(f2, new Fraction(1,4));
	}

}