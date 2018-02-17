package org.agohr.schiftschedule.constraints;

import java.util.Map;
import java.util.Optional;

import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.Constraint;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Shift;

/**
 * An employee must not be assigned two shifts at the same time.
 */
public class NoOverlappingShiftsConstraint implements Constraint {

	private static final NoOverlappingShiftsConstraint instance = new NoOverlappingShiftsConstraint();

	static NoOverlappingShiftsConstraint getInstance() {
		return instance;
	}

	private NoOverlappingShiftsConstraint() {
		// hidden
	}

	@Override
	public boolean violated(Assignment assignment, Shift shift, Employee employee, Assignment nextAssignment) {
		Optional<Employee> optEmployee = Optional.of(employee);
		return assignment.getAssignmentMap().entrySet().stream()
				.filter(entry -> entry.getValue().equals(optEmployee))
				.map(Map.Entry::getKey)
				.anyMatch(s -> s.overlap(shift));
	}

}
