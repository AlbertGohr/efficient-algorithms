package org.agohr.harmony.notes;

import lombok.Data;

@Data
public class Pitch {
	
	private static final int octaveSize = OctavePitch.values().length; // 12
	private static final int octaveOffset = 5;

	private final int midiValue;
	
	public Pitch(int midiValue) {
        assert 0 <= midiValue && midiValue < 128;
		this.midiValue = midiValue;
	}
	
	public Pitch(OctavePitch octavePitch, int octave) {
		this(computeMidiValue(octavePitch, octave));
	}

	private static int computeMidiValue(OctavePitch octavePitch, int octave) {
		assert -5 <= octave && octave <= 5;
		return octaveSize * (octave + octaveOffset) + octavePitch.ordinal();
	}

	public OctavePitch getOctavePitch() {
		return OctavePitch.values()[midiValue % octaveSize];
	}

	public int getOctave() {
		return midiValue / octaveSize - octaveOffset;
	}
	
	public Pitch halfToneStep(int halfTones) {
		return new Pitch(midiValue + halfTones);
	}
	
}
