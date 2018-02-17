package org.agohr.schiftschedule;

import lombok.ToString;

@ToString
public class ExpireCheck {

	private final long maxRuntimeMillisUntilAnySolution;
	private final long maxRuntimeMillisUntilOptimalSolution;

	private long expireTimeMillisBeforeAnySolution;
	private long expireTimeMillisBeforeOptimalSolution;

	ExpireCheck(long maxRuntimeMillis) {
		this(maxRuntimeMillis, maxRuntimeMillis);
	}

	ExpireCheck(long maxRuntimeMillisUntilOptimalSolution, long maxRuntimeMillisUntilAnySolution) {
		assert maxRuntimeMillisUntilAnySolution >= 0L;
		assert maxRuntimeMillisUntilOptimalSolution >= 0L;
		assert maxRuntimeMillisUntilAnySolution >= maxRuntimeMillisUntilOptimalSolution;
		this.maxRuntimeMillisUntilAnySolution = maxRuntimeMillisUntilAnySolution;
		this.maxRuntimeMillisUntilOptimalSolution = maxRuntimeMillisUntilOptimalSolution;
	}


	public void start() {
		long now = System.currentTimeMillis();
		expireTimeMillisBeforeOptimalSolution = now + maxRuntimeMillisUntilOptimalSolution;
		expireTimeMillisBeforeAnySolution = now + maxRuntimeMillisUntilAnySolution;
	}

	/**
	 * returns true if the program has expired since start has been called.
	 */
	public boolean expired(boolean solutionFound) {
		long now = System.currentTimeMillis();
		if (solutionFound) {
			return now >= expireTimeMillisBeforeOptimalSolution;
		}
		return now >= expireTimeMillisBeforeAnySolution;
	}

}
