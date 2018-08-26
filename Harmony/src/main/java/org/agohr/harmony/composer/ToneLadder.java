package org.agohr.harmony.composer;

import org.agohr.harmony.notes.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToneLadder implements Composer {

	/**
	 * Tone Ladder C-Dur.
	 *
	 * @return a new song.
	 */
	@Override
	public final List<Channel> compose() {
		final Fraction duration = new Fraction(1, 4);
		final List<Note> notes = new ArrayList<>();

		Pitch pitch = new Pitch(OctavePitch.C, 1);
		notes.add(new Note(pitch, duration));
		pitch = pitch.halfToneStep(2); // d
		notes.add(new Note(pitch, duration));
		pitch = pitch.halfToneStep(2); // e
		notes.add(new Note(pitch, duration));
		pitch = pitch.halfToneStep(1); // f
		notes.add(new Note(pitch, duration));
		pitch = pitch.halfToneStep(2); // g
		notes.add(new Note(pitch, duration));
		pitch = pitch.halfToneStep(2); // a
		notes.add(new Note(pitch, duration));
		pitch = pitch.halfToneStep(2); // h
		notes.add(new Note(pitch, duration));
		pitch = pitch.halfToneStep(1); // c
		notes.add(new Note(pitch, duration));
		return Collections.singletonList(new Channel(0, Instrument.shamisen, notes));
	}

}
