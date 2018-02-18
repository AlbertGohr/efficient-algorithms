package org.agohr.schiftschedule;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExpireCheckTest {

	@Test
	public void testExpired() {
		ExpireCheck expireCheck = new ExpireCheck(0L);
		expireCheck.start();
		assertTrue(expireCheck.expired(false));
		assertTrue(expireCheck.expired(true));
	}

	/**
	 * The time for finding at least one solution (with arbitrary quality) has not been expired. <br/>
	 * The time for finding the optimal solution has been expired.
	 */
	@Test
	public void testSemiExpired() {
		ExpireCheck expireCheck = new ExpireCheck(0L,10000L);
		expireCheck.start();
		assertFalse(expireCheck.expired(false));
		assertTrue(expireCheck.expired(true));
	}

	@Test
	public void testNotExpired() {
		ExpireCheck expireCheck = new ExpireCheck(10000L);
		expireCheck.start();
		assertFalse(expireCheck.expired(false));
		assertFalse(expireCheck.expired(true));
	}



}