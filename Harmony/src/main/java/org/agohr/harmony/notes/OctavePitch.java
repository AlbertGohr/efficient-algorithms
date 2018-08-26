package org.agohr.harmony.notes;

/**
 * An Octave consists of 12 half-note-steps.
 */
public enum OctavePitch {
	
	C, C_SHARP,D, D_SHARP,E,F, F_SHARP,G, G_SHARP,A, A_SHARP,B;

	/**
	 * @return true if this is a black keyboard key, false if this is a white keyboard key.
	 */
	public boolean isBlack() {
		return this == C_SHARP || this == D_SHARP || this == F_SHARP || this == G_SHARP || this == A_SHARP;
	}

	public OctavePitch halfToneStep(int steps) {
		OctavePitch[] values = OctavePitch.values();
		int length = values.length;
		int i = this.ordinal();
		i = (i + steps) % length;
		if (i < 0) {
			i += length;
		}
		return values[i];
	}

	public static OctavePitch byName(String name) {
		switch(name) {
			case "B#":
			case "C":
				return C;
			case "C#":
			case "Db":
				return C_SHARP;
			case "D":
				return D;
			case "D#":
			case "Eb":
				return D_SHARP;
			case "E":
			case "Fb":
				return E;
			case "E#":
			case "F":
				return F;
			case "F#":
			case "Gb":
				return F_SHARP;
			case "G":
				return G;
			case "G#":
			case "Ab":
				return G_SHARP;
			case "A":
				return A;
			case "A#":
			case "Bb":
				return A_SHARP;
			case "B":
			case "Cb":
				return B;
			default:
				throw new IllegalArgumentException("Unknown OctavePitch: " + name);
		}
	}

}
