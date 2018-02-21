package org.agohr.schiftschedule;

public interface ExpireCheck {
	void start();

	boolean expired(boolean solutionFound);
}
