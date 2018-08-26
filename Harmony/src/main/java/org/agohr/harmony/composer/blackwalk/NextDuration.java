package org.agohr.harmony.composer.blackwalk;

import org.agohr.harmony.Configuration;
import org.agohr.harmony.notes.Fraction;

import java.util.Random;

class NextDuration {

	private final Fraction minDuration;
	private final Fraction maxDuration;

	private final Random rnd;

	NextDuration(Random rnd, Configuration conf) {
		this.rnd = rnd;
		minDuration = new Fraction(1, conf.getMinDuration());
		maxDuration = new Fraction((1), conf.getMaxDuration());
	}

	Fraction computeNextDuration(Fraction duration) {
		double d = rnd.nextDouble();
		return nextDuration(duration, d);
	}

	/**
	 * computes the next duration based on the old one and random value d.
	 *
	 * @param duration the old duration
	 * @param d        random value [0..1]
	 * @return new duration
	 */
	private Fraction nextDuration(Fraction duration, double d) {
		assert new Fraction(1, 64).lessOrEqual(minDuration);
		assert minDuration.lessOrEqual(duration);
		assert duration.lessOrEqual(maxDuration);
		assert maxDuration.lessOrEqual(new Fraction(1, 1));
		assert 0.0 <= d && d <= 1.0;
		double pChange = 0.15;

		if (d < pChange) {
			return minDuration.less(duration) ? duration.multiply(new Fraction(1, 2)) : duration.multiply(2);
		} else if (d < pChange * 2) {
			return duration.less(maxDuration) ? duration.multiply(2) : duration.multiply(new Fraction(1, 2));
		} else {
			return duration;
		}
	}

}
