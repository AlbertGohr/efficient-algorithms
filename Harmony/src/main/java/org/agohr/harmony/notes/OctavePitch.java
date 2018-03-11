package org.agohr.harmony.notes;

/**
 * An Octave consists of 12 half-note-steps.
 */
public enum OctavePitch {
	
	C,Cis,D,Dis,E,F,Fis,G,Gis,A,Ais,B;

	/**
	 * @return true if this is a black keyboard key, false if this is a white keyboard key.
	 */
	public boolean isBlack() {
		return this == Cis || this == Dis || this == Fis || this == Gis || this == Ais;
	}

}
