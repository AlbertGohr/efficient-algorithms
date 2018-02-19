package org.agohr.schiftschedule.constraints;

import org.agohr.schiftschedule.Constraint;
import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Shift;

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
		assignment = assignment.add(shift, employee);
		Map<Employee, Long> employeeCounts = employeeAssignmentCounts(assignment);
		long available = countUnassignedShifts(assignment);
		long employeeAssignments = employeeCounts.getOrDefault(employee,0L);
		return toManyAssigned(employee, employeeAssignments) || notEnoughAvailable(employeeCounts, available);
	}

	private Map<Employee, Long> employeeAssignmentCounts(Assignment assignment) {
		return assignment.stream()
				.map(Map.Entry::getValue)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.groupingBy(
						identity(),
						Collectors.counting()
				));
	}

	private long countUnassignedShifts(Assignment assignment) {
		return assignment.stream()
				.filter(entry -> !entry.getValue().isPresent())
				.count();

	}

	private boolean notEnoughAvailable(Map<Employee, Long> counts, long available) {
		int missing = 0;
		for (Map.Entry<Employee, Long> count : counts.entrySet()) {
			Employee employee = count.getKey();
			long assigned = count.getValue();
			missing += employee.getClaimedNumberOfAssignments() - assigned;
		}
		return missing > available;
	}

	private boolean toManyAssigned(Employee employee, long assignments) {
		return assignments > employee.getClaimedNumberOfAssignments();
	}

}
