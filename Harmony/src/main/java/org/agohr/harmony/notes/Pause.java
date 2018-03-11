package org.agohr.harmony.notes;

public class Pause extends Note {
	
	public Pause(Fraction duration) {
		super(new NoPitch(),duration,new Velocity(0));
	}

}
