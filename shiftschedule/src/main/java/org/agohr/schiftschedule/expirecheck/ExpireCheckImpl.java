package org.agohr.schiftschedule.expirecheck;

import lombok.ToString;
import org.agohr.schiftschedule.ExpireCheck;

@ToString
public class ExpireCheckImpl implements ExpireCheck {

	private final long maxRuntimeMillisUntilAnySolution;
	private final long maxRuntimeMillisUntilOptimalSolution;

	private long expireTimeMillisBeforeAnySolution;
	private long expireTimeMillisBeforeOptimalSolution;

	public ExpireCheckImpl(long maxRuntimeMillis) {
		this(maxRuntimeMillis, maxRuntimeMillis);
	}

	public ExpireCheckImpl(long maxRuntimeMillisUntilOptimalSolution, long maxRuntimeMillisUntilAnySolution) {
		assert maxRuntimeMillisUntilAnySolution >= 0L;
		assert maxRuntimeMillisUntilOptimalSolution >= 0L;
		assert maxRuntimeMillisUntilAnySolution >= maxRuntimeMillisUntilOptimalSolution;
		this.maxRuntimeMillisUntilAnySolution = maxRuntimeMillisUntilAnySolution;
		this.maxRuntimeMillisUntilOptimalSolution = maxRuntimeMillisUntilOptimalSolution;
	}

	@Override
	public void start() {
		long now = System.currentTimeMillis();
		expireTimeMillisBeforeOptimalSolution = now + maxRuntimeMillisUntilOptimalSolution;
		expireTimeMillisBeforeAnySolution = now + maxRuntimeMillisUntilAnySolution;
	}

	/**
	 * returns true if the program has expired since start has been called.
	 */
	@Override
	public boolean expired(boolean solutionFound) {
		long now = System.currentTimeMillis();
		if (solutionFound) {
			return now >= expireTimeMillisBeforeOptimalSolution;
		}
		return now >= expireTimeMillisBeforeAnySolution;
	}

}
