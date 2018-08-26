package org.agohr.harmony.composer.harmonywalk;

import org.agohr.harmony.Configuration;
import org.agohr.harmony.math.PoissonDistribution;
import org.agohr.harmony.notes.Pitch;
import org.agohr.harmony.scale.Scale;
import org.agohr.harmony.scale.ScaleFactory;

import java.util.Random;

/**
 * next pitch = last pitch + steps. <br/>
 * a step leads to the next adjacent note due to the given scale. <br/>
 * steps are 50/50 up or down. <br/>
 * number of steps are based on a poisson distribution. <br/>
 * if all steps violate min/max, the steps are reversed ("keep moving"). <br/>
 * if some but not all steps violate min/max, the last note before min/max is selected.
 */
class NextPitch {

	private final Pitch minPitch;
	private final Pitch maxPitch;

	private final Random rnd;
	private final PoissonDistribution poisson;

	private final Scale scale;

	NextPitch(Random rnd, Configuration conf) {
		this.rnd = rnd;
		poisson = new PoissonDistribution(rnd, conf.getPoissonLambda());
		minPitch = new Pitch(conf.getMinPitch());
		maxPitch = new Pitch(conf.getMaxPitch());
		ScaleFactory scaleFactory = new ScaleFactory();
		scale = scaleFactory.byName(conf.getScale());
	}

	Pitch computeNextPitch(Pitch pitch) {
		assert scale.isInScale(pitch);
		int p = poisson.value();
		boolean neg = rnd.nextBoolean();
		if (neg) {
			if (scale.down(pitch).getMidiValue() < minPitch.getMidiValue()) {
				neg = false;
			}
		} else {
			if (scale.up(pitch).getMidiValue() < minPitch.getMidiValue()) {
				neg = true;
			}
		}
		int step = neg ? -p : p;
		return this.moveInScale(pitch, step);
	}

	/**
	 * @param pitch current pitch.
	 * @param step  number of notes in scale moved, neg. if downwards
	 * @return next pitch.
	 */
	private Pitch moveInScale(Pitch pitch, int step) {
		if (step > 0) {
			Pitch next = scale.up(pitch);
			if (next.getMidiValue() > maxPitch.getMidiValue()) {
				return pitch;
			}
			return moveInScale(next, step - 1);
		} else if (step < 0) {
			Pitch next = scale.down(pitch);
			if (next.getMidiValue() < minPitch.getMidiValue()) {
				return pitch;
			}
			return moveInScale(next, step + 1);
		} else { // step == 0
			return pitch;
		}
	}



}
