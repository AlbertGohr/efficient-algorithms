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
 * if all steps violate min/max, the steps are reversed ("keep moving"). <br/>
 * if some but not all steps violate min/max, the last note before min/max is selected.
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
		if (neg) {
			if (downBlackKey(pitch).getMidiValue() < minPitch.getMidiValue()) {
				neg = false;
			}
		} else {
			if (upBlackKey(pitch).getMidiValue() < minPitch.getMidiValue()) {
				neg = true;
			}
		}
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

		if (step > 0) {
			Pitch next = upBlackKey(pitch);
			if (next.getMidiValue() > maxPitch.getMidiValue()) {
				return pitch;
			}
			return blackMove(next, step - 1);
		} else if (step < 0) {
			Pitch next = downBlackKey(pitch);
			if (next.getMidiValue() < minPitch.getMidiValue()) {
				return pitch;
			}
			return blackMove(next, step + 1);
		} else { // step == 0
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
