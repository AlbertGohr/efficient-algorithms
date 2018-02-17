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

	private final Integer rating;

	public Rating(int rating) {
		assert min <= rating && rating <= max;
		this.rating = rating;
	}

	@Override
	public int compareTo(Rating other) {
		return Integer.compare(rating, other.rating);
	}
}
