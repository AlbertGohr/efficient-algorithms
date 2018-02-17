package org.agohr.schiftschedule.upperbounds;

import lombok.Value;
import org.agohr.schiftschedule.Assignment;
import org.agohr.schiftschedule.UpperBoundStrategy;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Rating;
import org.agohr.schiftschedule.vo.Shift;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * fixed upper bound for every shift, based on employee preferences.
 */
@Value
public class CandidateUpperBoundStrategy implements UpperBoundStrategy {

	private final Set<Employee> employees;

	private final Map<Shift, Rating> upperBounds = new HashMap<>();

	@Override
	public Rating getUpperBound(Shift shift, Assignment assignment) {
		return upperBounds.computeIfAbsent(shift, this::computeUpperBound);
	}

	private Rating computeUpperBound(Shift shift) {
		return employees.stream()
				.filter(e -> e.hasCandidate(shift))
				.map(e -> e.preferenceOf(shift))
				.max(Rating::compareTo)
				.orElseThrow(IllegalStateException::new);
	}
}
