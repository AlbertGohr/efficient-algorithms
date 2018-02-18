package org.agohr.schiftschedule.vo;

import org.junit.Test;

import static org.junit.Assert.*;

public class RatingTest {

	@Test
	public void testAvg() {
		int sum = (Rating.MaxRating.getRating() + Rating.MinRating.getRating());
		int actualSum = Rating.avg.getRating() * 2;
		assertEquals(sum, actualSum);
	}

}