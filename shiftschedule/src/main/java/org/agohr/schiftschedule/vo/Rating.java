package org.agohr.schiftschedule.vo;

import lombok.Value;

/**
 * a value from [min,max]
 */
@Value
public class Rating implements Comparable<Rating> {

	public static final int min = 1;
	public static final int max = 5;

	private final Integer rating;

	public Rating(int rating) {
		if (rating < min| max < rating) {
			throw new IllegalArgumentException();
		}
		this.rating = rating;
	}

	@Override
	public int compareTo(Rating other) {
		return Integer.compare(rating, other.rating);
	}
}
