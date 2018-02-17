package org.agohr.schiftschedule;

import lombok.Value;
import org.agohr.schiftschedule.vo.Assignment;

import java.util.Optional;

/**
 * assign an employee to every shift. <br/>
 * Satisfy all constraints. <br/>
 * maximize quality based on employee preferences.
 */
@Value
public class BranchAndBound {

	private final Configuration conf;
	private final Data data;

	public Optional<Assignment> compute() {
		conf.getExpireCheck().start();
		if (data.getShifts().isEmpty()) {
			return emptyAssignment();
		}
		Node root = createRoot();
		AssignmentQuality bestSolutionSoFar = new AssignmentQuality(null, Integer.MIN_VALUE);
		AssignmentQuality solution = root.compute(bestSolutionSoFar);
		return Optional.ofNullable(solution.getAssignment());
	}

	private Optional<Assignment> emptyAssignment() {
		Assignment assignment = new Assignment();
		return Optional.of(assignment);
	}

	private Node createRoot() {
		Assignment assignment = new Assignment(data.getShifts());
		AssignmentQuality aq = new AssignmentQuality(assignment, Integer.MIN_VALUE);
		return new Node(aq, conf, data);
	}

}
