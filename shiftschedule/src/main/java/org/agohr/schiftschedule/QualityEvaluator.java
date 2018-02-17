package org.agohr.schiftschedule;

import lombok.Value;
import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Rating;
import org.agohr.schiftschedule.vo.Shift;

import java.util.Map;
import java.util.Optional;

@Value
public class QualityEvaluator {

	private final UpperBoundStrategy upperBoundStrategy;

	int computeQuality(Assignment assignment) {
		return assignment.stream()
				.map(entry -> preference(entry, assignment))
				.map(Rating::getRating)
				.mapToInt(Integer::intValue)
				.sum();
	}

	private Rating preference(Map.Entry<Shift, Optional<Employee>> entry, Assignment assignment) {
		Shift shift = entry.getKey();
		Optional<Employee> employee = entry.getValue();
		return employee.map(e -> e.preferenceOf(shift))
				.orElseGet(() -> upperBoundStrategy.getUpperBound(shift, assignment));
	}

}
