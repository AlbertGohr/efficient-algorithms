package org.agohr.schiftschedule.upperbounds;

import lombok.RequiredArgsConstructor;
import org.agohr.schiftschedule.UpperBoundStrategy;
import org.agohr.schiftschedule.constraints.OrderedConstraints;
import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Employees;
import org.agohr.schiftschedule.vo.Rating;
import org.agohr.schiftschedule.vo.Shift;

/**
 * Considers current assignment and constraints.
 */
@RequiredArgsConstructor
public class ConstraintsAwareUpperBoundStrategy implements UpperBoundStrategy {

	private final Employees employees;

	private final OrderedConstraints constraints;

	@Override
	public Rating getUpperBound(Shift shift, Assignment assignment) {
		return employees.stream()
				.filter(e -> constraints.anyViolated(assignment, shift, e))
				.map(e -> e.preferenceOf(shift))
				.max(Rating::compareTo)
				.orElseThrow(IllegalStateException::new);
	}


}
