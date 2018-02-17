package org.agohr.schiftschedule.upperbounds;

import org.agohr.schiftschedule.UpperBoundStrategy;
import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Rating;
import org.agohr.schiftschedule.vo.Shift;

/**
 * Constant upper bound.
 */
public class SimpleUpperBoundStrategy implements UpperBoundStrategy {

	@Override
	public Rating getUpperBound(Shift shift, Assignment assignment) {
		return Rating.MaxRating;
	}

}
