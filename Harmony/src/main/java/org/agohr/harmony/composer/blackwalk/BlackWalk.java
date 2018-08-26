package org.agohr.harmony.composer.blackwalk;

import org.agohr.harmony.composer.Composer;
import org.agohr.harmony.notes.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * creates a new song. <br/>
 * Only choosing black keyboard keys always sounds great. Pentatonic.
 */
public class BlackWalk implements Composer {

	private final NextPitch nextPitch;
	private final NextDuration nextDuration;

	public BlackWalk(Random rnd) {
		nextPitch = new NextPitch(rnd);
		nextDuration = new NextDuration(rnd);
	}

	/**
	 * Random walk along black keys.
	 *
	 * @return a new song.
	 */
	@Override
	public final List<Channel> compose() {
		int n = 5000;   // number of Notes
		Instrument instrument = Instrument.Shamisen;

		Fraction startDuration = new Fraction(1, 4);
		Pitch startPitch = new Pitch(OctavePitch.Cis, 1);

		List<Note> notes = this.blackWalk(n, startDuration, startPitch);
		return Collections.singletonList(new Channel(0, instrument, notes));
	}

	/**
	 * random walk along black keys.
	 *
	 * @param n             number of notes
	 * @param startDuration duration of first note
	 * @param startPitch    pitch of first note
	 * @return list of notes
	 */
	private List<Note> blackWalk(int n, Fraction startDuration, Pitch startPitch) {
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
