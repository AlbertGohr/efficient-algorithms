package org.agohr.schiftschedule.upperbounds;

import lombok.Value;
import org.agohr.schiftschedule.Assignment;
import org.agohr.schiftschedule.Constraint;
import org.agohr.schiftschedule.UpperBoundStrategy;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Rating;
import org.agohr.schiftschedule.vo.Shift;

import java.util.List;
import java.util.Set;

/**
 * Considers current assignment and constraints.
 */
@Value
public class ConstraintsAwareUpperBoundStrategy implements UpperBoundStrategy {

	private final Set<Employee> employees;

	private final List<Constraint> constraints;

	@Override
	public Rating getUpperBound(Shift shift, Assignment assignment) {
		return employees.stream()
				.filter(e -> constraintViolated(assignment, shift,e))
				.map(e -> e.preferenceOf(shift))
				.max(Rating::compareTo)
				.orElseThrow(IllegalStateException::new);
	}

	private boolean constraintViolated(Assignment assignment, Shift shift, Employee employee) {
		return constraints.stream().anyMatch(constraint -> constraint.violated(assignment, shift, employee));
	}

}
