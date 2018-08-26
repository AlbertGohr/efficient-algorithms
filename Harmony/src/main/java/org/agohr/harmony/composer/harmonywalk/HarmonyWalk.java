package org.agohr.harmony.composer.harmonywalk;

import org.agohr.harmony.Configuration;
import org.agohr.harmony.composer.Composer;
import org.agohr.harmony.notes.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * creates a new song.
 */
public class HarmonyWalk implements Composer {

	private final NextPitch nextPitch;
	private final NextDuration nextDuration;

	private final Configuration conf;

	public HarmonyWalk(Random rnd, Configuration conf) {
		this.conf = conf;
		nextPitch = new NextPitch(rnd, conf);
		nextDuration = new NextDuration(rnd, conf);
	}

	/**
	 * Random walk along the given scale.
	 *
	 * @return a new song.
	 */
	@Override
	public final List<Channel> compose() {
		int n = conf.getNoteCount();
		assert n >= 0;
		Instrument instrument = new Instrument(conf.getInstrument());

		Fraction startDuration = new Fraction(1, conf.getFirstDuration());
		Pitch startPitch = new Pitch(conf.getFirstPitch());

		List<Note> notes = this.walk(n, startDuration, startPitch);
		return Collections.singletonList(new Channel(0, instrument, notes));
	}

	/**
	 * random walk along the given scale.
	 *
	 * @param n             number of notes
	 * @param startDuration duration of first note
	 * @param startPitch    pitch of first note
	 * @return list of notes
	 */
	private List<Note> walk(int n, Fraction startDuration, Pitch startPitch) {
		assert 0 <= n;
		Fraction duration = startDuration;
		Pitch pitch = startPitch;
		List<Note> notes = new ArrayList<>();

		for (int i = 0; i < n; ++i) {
			pitch = nextPitch.computeNextPitch(pitch);
			duration = nextDuration.computeNextDuration(duration);
			Note note = new Note(pitch, duration);
			notes.add(note);
		}

		return notes;
	}

}
