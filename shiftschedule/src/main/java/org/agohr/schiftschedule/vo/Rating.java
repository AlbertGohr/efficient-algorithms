package org.agohr.schiftschedule.vo;

import lombok.Value;

/**
 * a value from [min,max]
 */
@Value
public class Rating implements Comparable<Rating> {

	private static final int min = 1;
	private static final int max = 5;

	public static final Rating MinRating = new Rating(min);
	public static final Rating MaxRating = new Rating(max);
	public static final Rating avg = new Rating(computeAvg());

	private final Integer rating;

	public Rating(int rating) {
		assert min <= rating && rating <= max;
		this.rating = rating;
	}

	@Override
	public int compareTo(Rating other) {
		return Integer.compare(rating, other.rating);
	}

	private static int computeAvg() {
		int dif = MaxRating.getRating() - MinRating.getRating();
		assert dif % 2 == 0;
		return MinRating.getRating() + dif / 2;
	}

}
