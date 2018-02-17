package org.agohr.schiftschedule;

import lombok.Builder;
import org.agohr.schiftschedule.constraints.OrderedConstraints;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Employees;
import org.agohr.schiftschedule.vo.Shift;
import org.agohr.schiftschedule.vo.Shifts;

import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.logging.Logger;

/**
 * assign an employee to every shift. <br/>
 * Satisfy all constraints. <br/>
 * maximize quality based on employee preferences.
 */
@Builder
public class BranchAndBound {

	private static final Logger LOGGER = Logger.getLogger(BranchAndBound.class.getName());

	private final OrderedConstraints constraints;
	private final Employees employees;
	private final Shifts shifts;
	private final ExpireCheck expireCheck;
	private final UpperBoundStrategy upperBoundStrategy;

	public OptionalAssignment compute() {
		expireCheck.start();
		Assignment assignment = emptyAssignment();
		OptionalAssignment solution = new OptionalAssignment(null);
		return compute(solution, assignment);
	}

	private OptionalAssignment compute(OptionalAssignment solution, Assignment node) {
		Optional<Shift> nextUnassigned = node.getNextUnassigned();
		if (!nextUnassigned.isPresent()) {
			LOGGER.info(node.toString());
			return new OptionalAssignment(node);
		}
		Shift shift = nextUnassigned.get();
		PriorityQueue<Assignment> children = getChildren(node, shift);
		return traverseChildren(solution, children);
	}

	private OptionalAssignment traverseChildren(OptionalAssignment solution, PriorityQueue<Assignment> children) {
		for (Assignment nextNode : children) {
			if (expireCheck.expired(solution.isPresent())) {
				return solution;
			}
			if (nextNode.getQuality() <= solution.getQuality()) {
				continue;
			}
			OptionalAssignment result = compute(solution, nextNode);
			if (result.getQuality() > solution.getQuality()) {
				solution = result;
			}
		}
		return solution;
	}

	private PriorityQueue<Assignment> getChildren(Assignment node, Shift shift) {
		PriorityQueue<Assignment> children = new PriorityQueue<>(Comparator.comparingInt(Assignment::getQuality));
		employees.stream()
				.filter(e -> !constraintViolated(node, shift, e))
				.map(e -> node.assign(shift, e))
				.forEach(children::add);
		return children;
	}

	private boolean constraintViolated(Assignment assignment, Shift shift, Employee employee) {
		return constraints.stream().anyMatch(constraint -> constraint.violated(assignment, shift, employee));
	}

	private Assignment emptyAssignment() {
		Assignment.AssignmentBuilder builder = new Assignment.AssignmentBuilder(employees, upperBoundStrategy);
		builder.init(this.shifts);
		return builder.build();
	}


}
