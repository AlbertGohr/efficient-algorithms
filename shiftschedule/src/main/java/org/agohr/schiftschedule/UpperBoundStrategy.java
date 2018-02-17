package org.agohr.schiftschedule;

import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Rating;
import org.agohr.schiftschedule.vo.Shift;

/**
 * upper bound for the quality of a branch-and-bound solution
 */
public interface UpperBoundStrategy {

	Rating getUpperBound(Shift shift, Assignment assignment);

}
