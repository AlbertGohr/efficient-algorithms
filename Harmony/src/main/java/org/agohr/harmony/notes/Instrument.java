package org.agohr.harmony.notes;

public enum Instrument {

	Shamisen(106);
	
	private final int midiValue;
	
	public int getMidiValue() {
		return midiValue;
	}

	Instrument(int midiValue) {
		assert 0<=midiValue && midiValue < 128;
		this.midiValue = midiValue;
	}
	
}
