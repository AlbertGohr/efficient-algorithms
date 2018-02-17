package org.agohr.schiftschedule;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ExpireCheck {

	private final long maxRuntimeMillisUntilAnySolution;
	private final long maxRuntimeMillisUntilOptimalSolution;

	private long expireTimeMillisBeforeAnySolution;
	private long expireTimeMillisBeforeOptimalSolution;

	public ExpireCheck(long maxRuntimeMillis) {
		this(maxRuntimeMillis, maxRuntimeMillis);
	}

	public ExpireCheck(long maxRuntimeMillisUntilOptimalSolution, long maxRuntimeMillisUntilAnySolution) {
		assert maxRuntimeMillisUntilAnySolution >= maxRuntimeMillisUntilOptimalSolution;
		assert maxRuntimeMillisUntilOptimalSolution >= 0L;
		this.maxRuntimeMillisUntilAnySolution = maxRuntimeMillisUntilAnySolution;
		this.maxRuntimeMillisUntilOptimalSolution = maxRuntimeMillisUntilOptimalSolution;
	}


	public void start() {
		long now = System.currentTimeMillis();
		expireTimeMillisBeforeOptimalSolution = now + maxRuntimeMillisUntilOptimalSolution;
		expireTimeMillisBeforeAnySolution = now + maxRuntimeMillisUntilAnySolution;
	}

	public boolean expired(boolean solutionFound) {
		long now = System.currentTimeMillis();
		if (solutionFound) {
			return now >= expireTimeMillisBeforeOptimalSolution;
		}
		return now >= expireTimeMillisBeforeAnySolution;
	}
}
