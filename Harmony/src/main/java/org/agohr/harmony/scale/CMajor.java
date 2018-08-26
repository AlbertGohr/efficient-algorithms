package org.agohr.harmony.scale;

import org.agohr.harmony.notes.OctavePitch;
import org.agohr.harmony.notes.Pitch;

public class CMajor implements Scale {

	@Override
	public boolean isInScale(Pitch pitch) {
		OctavePitch octavePitch = pitch.getOctavePitch();
		switch (octavePitch) {
			case C:
			case D:
			case E:
			case F:
			case G:
			case A:
			case B:
				return true;
			default:
				return false;
		}
	}

	@Override
	public Pitch up(Pitch pitch) {
		OctavePitch octavePitch = pitch.getOctavePitch();
		switch (octavePitch) {
			case C:
				return pitch.halfToneStep(2);
			case D:
				return pitch.halfToneStep(2);
			case E:
				return pitch.halfToneStep(1);
			case F:
				return pitch.halfToneStep(2);
			case G:
				return pitch.halfToneStep(2);
			case A:
				return pitch.halfToneStep(2);
			case B:
				return pitch.halfToneStep(1);
			default:
				throw new IllegalStateException(pitch + " is not C-major");
		}
	}

	@Override
	public Pitch down(Pitch pitch) {
		OctavePitch octavePitch = pitch.getOctavePitch();
		switch (octavePitch) {
			case C:
				return pitch.halfToneStep(-1);
			case D:
				return pitch.halfToneStep(-2);
			case E:
				return pitch.halfToneStep(-2);
			case F:
				return pitch.halfToneStep(-1);
			case G:
				return pitch.halfToneStep(-2);
			case A:
				return pitch.halfToneStep(-2);
			case B:
				return pitch.halfToneStep(-2);
			default:
				throw new IllegalStateException(pitch + " is not C-major");
		}
	}
}
