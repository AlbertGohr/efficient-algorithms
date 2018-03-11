package org.agohr.harmony.composer;

import java.util.*;

import org.agohr.harmony.notes.Channel;
import org.agohr.harmony.notes.Fraction;
import org.agohr.harmony.notes.Instrument;
import org.agohr.harmony.notes.Note;
import org.agohr.harmony.notes.OctavePitch;
import org.agohr.harmony.notes.Pitch;
import org.agohr.harmony.math.PoissonDistribution;

/**
 * creates a new song. <br/>
 * Only choosing black keyboard keys always sounds great. Pentatonic.
 */
public class BlackWalk implements Composer {

	private final Fraction minDuration = new Fraction(1, 16);
	private final Fraction maxDuration = new Fraction(1, 2);

	private final Pitch minPitch = new Pitch(OctavePitch.C, -1);
	private final Pitch maxPitch = new Pitch(OctavePitch.B, 2);

	private final Random rnd;

	public BlackWalk(Random rnd) {
		this.rnd = rnd;
	}

	/**
	 * Random walk along black keys.
	 *
	 * @return a new song.
	 */
	@Override
	public final List<Channel> compose() {
		final int n = 5000;   // number of Notes
		final Instrument instrument = Instrument.Shamisen;

		final Fraction startDuration = new Fraction(1, 4);
		final Pitch startPitch = new Pitch(OctavePitch.Cis, 1);

		final List<Note> notes0 = this.blackWalk(n, startDuration, startPitch);
		return Collections.singletonList(new Channel(0, instrument, notes0));
	}

	/**
	 * random walk along black keys.
	 *
	 * @param n             number of notes
	 * @param startDuration duration of first note
	 * @param startPitch    pitch of first note
	 * @return list of notes
	 */
	private List<Note> blackWalk(final int n, final Fraction startDuration, final Pitch startPitch) {
		assert 0 <= n;
		Fraction duration = startDuration;
		Pitch pitch = startPitch;
		final List<Note> notes = new ArrayList<>();
		final PoissonDistribution poisson = new PoissonDistribution(rnd);

		for (int i = 0; i < n; ++i) {
			final int p = poisson.value();
			final boolean neg = rnd.nextBoolean();
			final int step = neg ? -p : p;
			pitch = this.blackMove(pitch, step);

			final double d = rnd.nextDouble();
			duration = nextDuration(duration, d);

			final Note note = new Note(pitch, duration);
			notes.add(note);
		}

		return notes;
	}

	/**
	 * computes the next duration based on the old one and random value d.
	 *
	 * @param duration the old duration
	 * @param d        random value [0..1]
	 * @return new duration
	 */
	private Fraction nextDuration(final Fraction duration, final double d) {
		assert new Fraction(1, 64).lessOrEqual(minDuration);
		assert minDuration.lessOrEqual(duration);
		assert duration.lessOrEqual(maxDuration);
		assert maxDuration.lessOrEqual(new Fraction(1, 1));
		assert 0.0 <= d && d <= 1.0;
		final double pChange = 0.15;

		if (d < pChange) {
			return minDuration.less(duration) ? duration.multiply(new Fraction(1, 2)) : duration.multiply(2);
		} else if (d < pChange * 2) {
			return duration.less(maxDuration) ? duration.multiply(2) : duration.multiply(new Fraction(1, 2));
		} else {
			return duration;
		}
	}

	/**
	 * select next black key.
	 *
	 * @param pitch current black key.
	 * @param step  number of black keys moved
	 * @return neighboured black key.
	 */
	private Pitch blackMove(final Pitch pitch, int step) {
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
