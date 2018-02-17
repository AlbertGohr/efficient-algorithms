package org.agohr.schiftschedule.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class Assignment {

	@Getter(AccessLevel.NONE)
	private final Map<Shift, Optional<Employee>> assignment;

	/**
	 * There are no shifts to be assigned.
	 */
	public Assignment() {
		this(new HashMap<>());
	}

	/**
	 * There are shifts to be assigned, but none has been assigned yet.
	 */
	public Assignment(Shifts shifts) {
		this(noAssignment(shifts));
	}

	private Assignment(Map<Shift, Optional<Employee>> assignment) {
		this.assignment = Collections.unmodifiableMap(assignment);
	}


	private static Map<Shift, Optional<Employee>> noAssignment(Shifts shifts) {
		Map<Shift, Optional<Employee>> assignment = new HashMap<>();
		shifts.stream()
				.forEach(shift -> assignment.put(shift, Optional.empty()));
		return assignment;
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

	@Override
	public String toString() {
		String toString = stream()
				.map(this::entryString)
				.collect(Collectors.joining(","));
		return "Assignment (shiftId:employeeId) [" + toString + "]";
	}

	private String entryString(Map.Entry<Shift, Optional<Employee>> entry) {
		Optional<Employee> employee = entry.getValue();
		String value = employee.map(e -> String.valueOf(e.getId())).orElse("/");
		return entry.getKey().getId() + ":" + value;
	}

}
