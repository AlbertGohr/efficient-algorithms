package org.agohr.harmony.math;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class GreatestCommonDivisorTest {

	@Test
	public void test() {
		GreatestCommonDivisor service = new GreatestCommonDivisor();
		int gcd = service.compute(2, 8);
		assertEquals(2, gcd);
	}

}
