package org.agohr.harmony.notes;

public class Instrument {

	public static final Instrument shamisen = new Instrument(106);
	
	private final int midiValue;
	
	public int getMidiValue() {
		return midiValue;
	}

	public Instrument(int midiValue) {
		assert 0<=midiValue && midiValue < 128;
		this.midiValue = midiValue;
	}
	
}
