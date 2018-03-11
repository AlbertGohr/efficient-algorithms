package org.agohr.harmony.notes;

/**
 * Every note has a pitch or no pitch. <br/>
 * A pause is a note without pitch.
 */
public class NoPitch extends Pitch {

	public NoPitch() {
		super(0);
	}
	
	@Override
    public String toString() {
		return "NoPitch";
    }

}
