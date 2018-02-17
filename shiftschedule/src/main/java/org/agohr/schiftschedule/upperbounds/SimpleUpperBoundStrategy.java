package org.agohr.schiftschedule.upperbounds;

import lombok.Value;
import org.agohr.schiftschedule.Assignment;
import org.agohr.schiftschedule.UpperBoundStrategy;
import org.agohr.schiftschedule.vo.Rating;
import org.agohr.schiftschedule.vo.Shift;

/**
 * Constant upper bound.
 */
@Value
public class SimpleUpperBoundStrategy implements UpperBoundStrategy {

	private static final Rating MAX = new Rating(Rating.max);

	@Override
	public Rating getUpperBound(Shift shift, Assignment assignment) {
		return MAX;
	}

}
