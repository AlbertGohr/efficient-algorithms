package org.agohr.harmony.composer.harmonywalk;

import org.agohr.harmony.Configuration;
import org.agohr.harmony.notes.Fraction;

import java.util.Random;

/**
 * with p = rho/2 diminish duration by half <br/>
 * with p = rho/2 double duration <br/>
 * with p = 1-rho leave duration unchanged. <br/>
 * if the change violates min/max duration, the change is reversed. ("keep changing")
 */
class NextDuration {

	private final Fraction minDuration;
	private final Fraction maxDuration;

	private final Random rnd;

	private final double rho;

	NextDuration(Random rnd, Configuration conf) {
		rho = conf.getDurationRho();
		assert 0 <= rho && rho <= 1;
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

		if (d < rho/2) {
			return minDuration.less(duration) ? duration.multiply(new Fraction(1, 2)) : duration.multiply(2);
		} else if (d < rho) {
			return duration.less(maxDuration) ? duration.multiply(2) : duration.multiply(new Fraction(1, 2));
		} else {
			return duration;
		}
	}

}
