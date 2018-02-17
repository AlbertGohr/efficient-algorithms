package org.agohr.schiftschedule;

import lombok.Value;
import org.agohr.schiftschedule.vo.*;

import java.util.*;
import java.util.logging.Logger;

@Value
class Node {

	private static final Logger LOGGER = Logger.getLogger(Node.class.getName());

	private final AssignmentQuality assignmentQuality;
	private final BranchAndBoundConfiguration conf;
	private final QualityEvaluator qualityEvaluator;

	Node(AssignmentQuality assignmentQuality, BranchAndBoundConfiguration conf) {
		this.assignmentQuality = assignmentQuality;
		this.conf = conf;
		this.qualityEvaluator = new QualityEvaluator(conf.getUpperBoundStrategy());
	}

	private int quality() {
		return assignmentQuality.getQuality();
	}

	AssignmentQuality compute(AssignmentQuality previouslyFoundSolution) {
		Optional<Shift> nextUnassignedShift = assignmentQuality.getAssignment().getNextUnassignedShift();
		if (!nextUnassignedShift.isPresent()) {
			LOGGER.info(assignmentQuality.toString());
			return assignmentQuality;
		}
		Shift shift = nextUnassignedShift.get();
		PriorityQueue<Node> children = getChildren(shift);
		return traverseChildren(previouslyFoundSolution, children);
	}

	private AssignmentQuality traverseChildren(AssignmentQuality solution, PriorityQueue<Node> children) {
		for (Node nextNode : children) {
			if (conf.getExpireCheck().expired(solution.isPresent())) {
				return solution;
			}
			if (nextNode.getAssignmentQuality().getQuality() <= solution.getQuality()) {
				continue;
			}
			AssignmentQuality result = nextNode.compute(solution);
			if (result.getQuality() > solution.getQuality()) {
				solution = result;
			}
		}
		return solution;
	}

	private PriorityQueue<Node> getChildren(Shift shift) {
		PriorityQueue<Node> children = new PriorityQueue<>(Comparator.comparingInt(Node::quality));
		conf.getEmployees().stream()
				.filter(e -> !conf.getConstraints().anyViolated(assignmentQuality.getAssignment(), shift, e))
				.map(e -> createChild(shift, e))
				.forEach(children::add);
		return children;
	}

	private Node createChild(Shift shift, Employee employee) {
		Assignment assigned = assignmentQuality.getAssignment().add(shift, employee);
		int quality = qualityEvaluator.computeQuality(assigned);
		AssignmentQuality assignedQuality = new AssignmentQuality(assigned, quality);
		return new Node(assignedQuality, conf);
	}

}
