package org.agohr.schiftschedule.upperbounds;

import org.agohr.schiftschedule.UpperBoundStrategy;
import org.agohr.schiftschedule.vo.Rating;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleUpperBoundStrategyTest {

	private final UpperBoundStrategy strategy = new SimpleUpperBoundStrategy();

	@Test
	public void testUpperBound() {
		Rating upperBound = strategy.getUpperBound(null, null);
		assertEquals(Rating.MaxRating, upperBound);
	}

}