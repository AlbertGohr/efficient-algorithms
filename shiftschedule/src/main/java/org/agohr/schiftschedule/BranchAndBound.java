package org.agohr.schiftschedule;

import lombok.RequiredArgsConstructor;
import org.agohr.schiftschedule.vo.Assignment;

import java.util.Optional;

/**
 * assign an employee to every shift. <br/>
 * Satisfy all constraints. <br/>
 * maximize quality based on employee preferences.
 */
@RequiredArgsConstructor
public class BranchAndBound {

	private final BranchAndBoundConfiguration conf;

	public Optional<Assignment> compute() {
		conf.getExpireCheck().start();
		Node root = createRoot();
		AssignmentQuality bestSolutionSoFar = new AssignmentQuality(null, Integer.MIN_VALUE);
		AssignmentQuality solution = root.compute(bestSolutionSoFar);
		return Optional.ofNullable(solution.getAssignment());
	}

	private Node createRoot() {
		Assignment assignment = new Assignment(conf.getShifts());
		// TODO special case: shifts are empty -> solution = empty assignment
		AssignmentQuality aq = new AssignmentQuality(assignment, Integer.MIN_VALUE);
		return new Node(aq, conf);
	}

}
