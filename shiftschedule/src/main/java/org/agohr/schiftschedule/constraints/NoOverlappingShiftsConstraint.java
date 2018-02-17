package org.agohr.schiftschedule.constraints;

import org.agohr.schiftschedule.Constraint;
import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Shift;

import java.util.Map;
import java.util.Optional;

/**
 * An employee must not be assigned two shifts at the same time.
 */
public class NoOverlappingShiftsConstraint implements Constraint {

	@Override
	public boolean violated(Assignment assignment, Shift shift, Employee employee) {
		Optional<Employee> optEmployee = Optional.of(employee);
		return assignment.getAssignment().entrySet().stream()
				.filter(entry -> entry.getValue().equals(optEmployee))
				.map(Map.Entry::getKey)
				.anyMatch(s -> s.overlap(shift));
	}

}
