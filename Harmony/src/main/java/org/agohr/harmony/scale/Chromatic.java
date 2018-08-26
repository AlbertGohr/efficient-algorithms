package org.agohr.harmony.scale;

import org.agohr.harmony.notes.Pitch;

public class Chromatic implements Scale {
	@Override
	public boolean isInScale(Pitch pitch) {
		return true;
	}

	@Override
	public Pitch up(Pitch pitch) {
		return new Pitch(pitch.getMidiValue() + 1 );
	}

	@Override
	public Pitch down(Pitch pitch) {
		return new Pitch(pitch.getMidiValue() - 1);
	}
}
