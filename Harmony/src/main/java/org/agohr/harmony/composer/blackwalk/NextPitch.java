package org.agohr.harmony.composer.blackwalk;

import org.agohr.harmony.Configuration;
import org.agohr.harmony.math.PoissonDistribution;
import org.agohr.harmony.notes.OctavePitch;
import org.agohr.harmony.notes.Pitch;

import java.util.Random;

/**
 * next pitch = last pitch + steps. <br/>
 * a step leads to the next adjacent note (here only black keys). <br/>
 * steps are 50/50 up or down. <br/>
 * number of steps are based on a poisson distribution. <br/>
 * if a step violates min/max pitch, this and the remaining steps are reversed.
 */
class NextPitch {

	private final Pitch minPitch;
	private final Pitch maxPitch;

	private final Random rnd;
	private final PoissonDistribution poisson;

	NextPitch(Random rnd, Configuration conf) {
		this.rnd = rnd;
		poisson = new PoissonDistribution(rnd, conf.getPoissonLambda());
		minPitch = new Pitch(conf.getMinPitch());
		maxPitch = new Pitch(conf.getMaxPitch());
	}

	Pitch computeNextPitch(Pitch pitch) {
		int p = poisson.value();
		boolean neg = rnd.nextBoolean();
		int step = neg ? -p : p;
		return this.blackMove(pitch, step);
	}

	/**
	 * select next black key.
	 *
	 * @param pitch current black key.
	 * @param step  number of black keys moved
	 * @return neighboured black key.
	 */
	private Pitch blackMove(Pitch pitch, int step) {
		OctavePitch octavePitch = pitch.getOctavePitch();
		assert octavePitch.isBlack();
		if (pitch.getMidiValue() < minPitch.getMidiValue() && step <= 0) {
			// move upwards if pitch is to low and not moving upwards
			step = step == 0 ? 1 : -step;
			return blackMove(pitch, step);
		}
		if (pitch.getMidiValue() > maxPitch.getMidiValue() && step >= 0) {
			// move downwards if pitch is to high and not moving downwards
			step = step == 0 ? -1 : -step;
			return blackMove(pitch, step);
		}
		if (step > 0) {
			// increasing step
			return this.blackMove(upBlackKey(pitch), step - 1);
		} else if (step < 0) {
			// decreasing step
			return this.blackMove(downBlackKey(pitch), step + 1);
		} else {
			// no steps left, select the black key
			return pitch;
		}
	}

	private Pitch upBlackKey(Pitch pitch) {
		OctavePitch octavePitch = pitch.getOctavePitch();
		switch (octavePitch) {
			case Cis:
			case Fis:
			case Gis:
				return pitch.halfToneStep(2);
			case Dis:
			case Ais:
				return pitch.halfToneStep(3);
			default:
				throw new IllegalStateException(pitch + " is no black key");
		}
	}

	private Pitch downBlackKey(Pitch pitch) {
		OctavePitch octavePitch = pitch.getOctavePitch();
		switch (octavePitch) {
			case Cis:
			case Fis:
				return pitch.halfToneStep(-3);
			case Dis:
			case Gis:
			case Ais:
				return pitch.halfToneStep(-2);
			default:
				throw new IllegalStateException(pitch + " is no black key");
		}
	}

}
