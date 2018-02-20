package org.agohr.schiftschedule;

import lombok.Value;
import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Shift;

import java.util.HashMap;
import java.util.Map;
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

	public Optional<Map<Shift,Employee>> compute() {
		conf.getExpireCheck().start();
		if (data.getShifts().isEmpty()) {
			return emptyAssignment();
		}
		Node root = createRoot();
		AssignmentQuality bestSolutionSoFar = new AssignmentQuality(null, Integer.MIN_VALUE);
		AssignmentQuality solution = root.compute(bestSolutionSoFar);
		Assignment assignment = solution.getAssignment();
		if (assignment == null) {
			return Optional.empty();
		}
		return Optional.of(flat(assignment));
	}

	private Map<Shift,Employee> flat(Assignment assignment) {
		Map<Shift, Employee> result = new HashMap<>();
		assignment.stream().forEach(entry -> result.put(entry.getKey(),entry.getValue().orElseThrow(NullPointerException::new)));
		return result;
	}

	private Optional<Map<Shift,Employee>> emptyAssignment() {
		return Optional.of(new HashMap<>());
	}

	private Node createRoot() {
		Assignment assignment = new Assignment(data.getShifts());
		AssignmentQuality aq = new AssignmentQuality(assignment, Integer.MIN_VALUE);
		return new Node(aq, conf, data);
	}

}
