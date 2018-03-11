package org.agohr.harmony.notes;

import lombok.Value;
import org.agohr.harmony.math.GreatestCommonDivisor;

/**
 * This fraction describes the rhythm.
 */
@Value
public class Fraction {

	private final int upper;
	
	private final int lower;
	
	public Fraction(int upper, int lower) {
		assert 0 < lower  && lower <= 128 && powerOf2(lower);
		assert 0<= upper && upper <= lower;
		assert new GreatestCommonDivisor().compute(upper, lower) == 1;
		assert upper != 0 || lower == 1;

		this.upper = upper;
		this.lower = lower;
	}

	private boolean powerOf2(int n) {
		return (n & (n - 1)) == 0;
	}
	
	public boolean lessOrEqual(Fraction other) {
		return upper * other.lower <= other.upper * lower;
	}

	public boolean less(Fraction other) {
		return upper * other.lower < other.upper * lower;
	}

	public Fraction multiply(int n) {
		int upper = this.upper * n;
		int lower = this.lower;
		int gcd = new GreatestCommonDivisor().compute(upper, lower);
		return new Fraction(upper/gcd, lower/gcd);
	}
	
	public Fraction multiply(Fraction other) {
		int upper = this.upper * other.upper;
		int lower = this.lower * other.lower;
		int gcd = new GreatestCommonDivisor().compute(upper, lower);
		return new Fraction(upper/gcd, lower/gcd);
	}
	
}
