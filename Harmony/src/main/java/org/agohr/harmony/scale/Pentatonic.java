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

	@Override
	public Pitch down(Pitch pitch) {
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
