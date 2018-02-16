package org.agohr.schiftschedule;

import lombok.RequiredArgsConstructor;
import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.OptionalAssignment;
import org.agohr.schiftschedule.vo.Shift;

import java.util.*;
import java.util.logging.Logger;

/**
 * assign an employee to every shift. <br/>
 * Satisfy all constraints. <br/>
 * maximize quality based on employee preferences.
 */
@RequiredArgsConstructor
public class BranchAndBound {

	private static final Logger LOGGER = Logger.getLogger(BranchAndBound.class.getName());

	private final List<Constraint> constraints;
	private final Set<Employee> employees;
	private final Set<Shift> shifts;

	private long expireTimeMillis;

	public OptionalAssignment compute(long maxRuntimeMillis) {
		start(maxRuntimeMillis);
		Assignment assignment = emptyAssignment();
		OptionalAssignment solution = new OptionalAssignment(null);
		return compute(solution, assignment);
	}

	private OptionalAssignment compute(OptionalAssignment solution, Assignment node) {
		if (expired()) {
			return solution;
		}
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
			if (expired()) {
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
		for (Employee employee : employees) {
			if (constraintViolated(node, shift, employee)) {
				continue;
			}
			Assignment child = assign(node, shift, employee);
			children.add(child);
		}
		return children;
	}

	private Assignment assign(Assignment node, Shift shift, Employee employee) {
		Assignment.AssignmentBuilder builder = new Assignment.AssignmentBuilder(node);
		builder.assign(shift, employee);
		return builder.build();
	}

	private boolean constraintViolated(Assignment assignment, Shift shift, Employee employee) {
		Assignment nextAssignment = assign(assignment, shift, employee);
		return constraints.stream().anyMatch(constraint -> constraint.violated(assignment, shift, employee, nextAssignment));
	}

	private Assignment emptyAssignment() {
		Assignment.AssignmentBuilder builder = new Assignment.AssignmentBuilder();
		builder.init(this.shifts);
		return builder.build();
	}

	private void start(long maxRuntimeMillis) {
		expireTimeMillis = System.currentTimeMillis() + maxRuntimeMillis;
	}

	private boolean expired() {
		return System.currentTimeMillis() >= expireTimeMillis;
	}

}
