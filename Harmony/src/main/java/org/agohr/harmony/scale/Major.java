package org.agohr.harmony.scale;

import org.agohr.harmony.notes.OctavePitch;
import org.agohr.harmony.notes.Pitch;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Major implements Scale {

	private final OctavePitch tonic;

	private final Map<OctavePitch, Integer> scale;

	public Major(OctavePitch tonic) {
		this.tonic = tonic;
		this.scale = Collections.unmodifiableMap(computeScale(tonic));
	}

	private Map<OctavePitch, Integer> computeScale(OctavePitch tonic) {
		Map<OctavePitch, Integer> scale = new HashMap<>();
		OctavePitch note = tonic;
		scale.put(note, 1);
		note = note.halfToneStep(2);
		scale.put(note, 2);
		note = note.halfToneStep(2);
		scale.put(note, 3);
		note = note.halfToneStep(1);
		scale.put(note, 4);
		note = note.halfToneStep(2);
		scale.put(note, 5);
		note = note.halfToneStep(2);
		scale.put(note, 6);
		note = note.halfToneStep(2);
		scale.put(note, 7);
		return scale;
	}

	@Override
	public boolean isInScale(Pitch pitch) {
		Integer note = scale.get(pitch.getOctavePitch());
		return note != null;
	}

	@Override
	public Pitch up(Pitch pitch) {
		Integer note = scale.get(pitch.getOctavePitch());
		if (note == null) {
			throw new IllegalArgumentException(pitch + " is not in scale " + tonic);
		}
		switch (note) {
			case 1:
			case 2:
			case 4:
			case 5:
			case 6:
				return pitch.halfToneStep(2);
			case 3:
			case 7:
				return pitch.halfToneStep(1);
			default:
				throw new IllegalStateException();
		}
	}

	@Override
	public Pitch down(Pitch pitch) {
		Integer note = scale.get(pitch.getOctavePitch());
		if (note == null) {
			throw new IllegalArgumentException(pitch + " is not in scale " + tonic);
		}
		switch (note) {
			case 2:
			case 3:
			case 5:
			case 6:
			case 7:
				return pitch.halfToneStep(-2);
			case 1:
			case 4:
				return pitch.halfToneStep(-1);
			default:
				throw new IllegalStateException();
		}
	}
}
