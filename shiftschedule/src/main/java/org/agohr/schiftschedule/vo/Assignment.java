package org.agohr.schiftschedule.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.util.*;
import java.util.stream.Stream;

@Value
public class Assignment {

	@Getter(AccessLevel.NONE)
	private final Map<Shift, Optional<Employee>> assignment;

	private Assignment(Map<Shift, Optional<Employee>> assignment) {
		this.assignment = Collections.unmodifiableMap(assignment);
	}

	/**
	 * empty assignment.
	 */
	public Assignment(Shifts shifts) {
		assignment = new HashMap<>();
		shifts.stream()
				.forEach(shift -> assignment.put(shift, Optional.empty()));
	}

	public Stream<Map.Entry<Shift, Optional<Employee>>> stream() {
		return assignment.entrySet().stream();
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
