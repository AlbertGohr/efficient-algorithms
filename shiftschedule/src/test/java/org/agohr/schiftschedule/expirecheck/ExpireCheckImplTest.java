package org.agohr.schiftschedule.expirecheck;

import org.agohr.schiftschedule.expirecheck.ExpireCheckImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpireCheckImplTest {

	@Test
	public void testExpired() {
		ExpireCheckImpl expireCheck = new ExpireCheckImpl(0L);
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
		ExpireCheckImpl expireCheck = new ExpireCheckImpl(0L,10000L);
		expireCheck.start();
		assertFalse(expireCheck.expired(false));
		assertTrue(expireCheck.expired(true));
	}

	@Test
	public void testNotExpired() {
		ExpireCheckImpl expireCheck = new ExpireCheckImpl(10000L);
		expireCheck.start();
		assertFalse(expireCheck.expired(false));
		assertFalse(expireCheck.expired(true));
	}



}