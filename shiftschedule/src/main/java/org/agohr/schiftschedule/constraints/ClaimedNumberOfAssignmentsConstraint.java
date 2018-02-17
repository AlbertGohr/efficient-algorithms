package org.agohr.schiftschedule.constraints;

import org.agohr.schiftschedule.Constraint;
import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Shift;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

/**
 * For each employee e there exists a number c_e of shifts that must be assigned to this employee. <br/>
 * Neither less no more shifts are assignable to the employee. <br/>
 * Ensure, that no employee has been assigned to many shifts. <br/>
 * Ensure, that there are enough unassigned shifts available to satisfy all employees.
 */
public class ClaimedNumberOfAssignmentsConstraint implements Constraint {

	@Override
	public boolean violated(Assignment assignment, Shift shift, Employee employee) {
		Map<Optional<Employee>, Long> extendedCounts = extendedCounts(assignment);
		Map<Employee, Long> employeeCounts = new HashMap<>(extendedCounts.size());
		long available = 0L;
		for (Map.Entry<Optional<Employee>, Long> entry : extendedCounts.entrySet()) {
			Optional<Employee> optionalEmployee = entry.getKey();
			Long count = entry.getValue();
			if (optionalEmployee.isPresent()) {
				employeeCounts.put(optionalEmployee.get(), count);
			} else {
				available = count;
			}
		}
		return tooManyAssigned(employeeCounts) || notEnoughAvailable(employeeCounts, available);
	}

	private boolean notEnoughAvailable(Map<Employee, Long> counts, long available) {
		int missing = 0;
		for (Map.Entry<Employee, Long> count : counts.entrySet()) {
			Employee employee = count.getKey();
			missing += employee.getClaimedNumberOfAssignments() - count.getValue();
		}
		return missing > available;
	}

	private boolean tooManyAssigned(Map<Employee, Long> counts) {
		for (Map.Entry<Employee, Long> count : counts.entrySet()) {
			Employee employee = count.getKey();
			Long assignments = count.getValue();
			if (employee.getClaimedNumberOfAssignments() < assignments) {
				return true;
			}
		}
		return false;
	}

	/**
	 * counts for every employee how often he has been assigned. <br/>
	 * counts also, how many shifts have not been assigned. Stored in key Optional.empty.
	 */
	private Map<Optional<Employee>, Long> extendedCounts(Assignment assignment) {
		return assignment.getAssignment().values().stream()
				.collect(Collectors.groupingBy(
						identity(),
						Collectors.counting()
				));
	}

}
