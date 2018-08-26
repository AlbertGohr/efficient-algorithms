package org.agohr.harmony.scale;

import org.agohr.harmony.notes.OctavePitch;
import org.agohr.harmony.notes.Pitch;

public class Pentatonic implements Scale {

	@Override
	public boolean isInScale(Pitch pitch) {
		return pitch.getOctavePitch().isBlack();
	}

	@Override
	public Pitch up(Pitch pitch) {
		OctavePitch octavePitch = pitch.getOctavePitch();
		switch (octavePitch) {
			case C_SHARP:
			case F_SHARP:
			case G_SHARP:
				return pitch.halfToneStep(2);
			case D_SHARP:
			case A_SHARP:
				return pitch.halfToneStep(3);
			default:
				throw new IllegalStateException(pitch + " is no black key");
		}
	}

	@Override
	public Pitch down(Pitch pitch) {
		OctavePitch octavePitch = pitch.getOctavePitch();
		switch (octavePitch) {
			case C_SHARP:
			case F_SHARP:
				return pitch.halfToneStep(-3);
			case D_SHARP:
			case G_SHARP:
			case A_SHARP:
				return pitch.halfToneStep(-2);
			default:
				throw new IllegalStateException(pitch + " is no black key");
		}
	}

}
