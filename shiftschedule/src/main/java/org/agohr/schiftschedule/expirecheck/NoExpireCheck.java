package org.agohr.schiftschedule.expirecheck;

import org.agohr.schiftschedule.ExpireCheck;

public class NoExpireCheck implements ExpireCheck {

	@Override
	public void start() {
		// nothing
	}

	@Override
	public boolean expired(boolean solutionFound) {
		return false;
	}

}
