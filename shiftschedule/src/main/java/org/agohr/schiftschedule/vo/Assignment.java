package org.agohr.schiftschedule.vo;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Value
@RequiredArgsConstructor
public class Assignment {

	// TODO make immutable
	private final Map<Shift, Optional<Employee>> assignment;

	/**
	 * empty assignment.
	 */
	public Assignment(Shifts shifts) {
		assignment = new HashMap<>();
		shifts.stream()
				.forEach(shift -> assignment.put(shift, Optional.empty()));
	}

	/**
	 * add (shift,employee) to the previous assignment
	 */
	public Assignment add(Shift shift, Employee employee) {
		Map<Shift, Optional<Employee>> assignment = new HashMap<>(this.assignment);
		assignment.put(shift, Optional.ofNullable(employee));
		return new Assignment(assignment);
	}

	public Optional<Shift> getNextUnassignedShift() {
		return assignment.entrySet().stream()
				.filter(entry -> !entry.getValue().isPresent())
				.map(Map.Entry::getKey)
				.min(Comparator.comparing(Shift::getStart));
	}

}
