package org.agohr.schiftschedule.vo;

import lombok.Value;

import java.util.*;

@Value
public class Assignment {

	private final Map<Shift, Optional<Employee>> assignmentMap;

	private final Set<Employee> employees;

	private final int quality;

	private Assignment(Map<Shift, Optional<Employee>> assignmentMap, Set<Employee> employees) {
		this.assignmentMap = Collections.unmodifiableMap(assignmentMap);
		this.employees = employees;
		quality = computeQuality();
	}

	public Optional<Shift> getNextUnassigned() {
		return assignmentMap.entrySet().stream()
				.filter(entry -> !entry.getValue().isPresent())
				.map(Map.Entry::getKey)
				.min(Comparator.comparing(Shift::getStart));
	}

	public Set<Shift> keys() {
		return assignmentMap.keySet();
	}

	public Optional<Employee> get(Shift shift) {
		return assignmentMap.get(shift);
	}

	private int computeQuality() {
		return assignmentMap.entrySet().stream()
				.map(this::preference)
				.map(Rating::getRating)
				.mapToInt(Integer::intValue)
				.sum();
	}

	private Rating preference(Map.Entry<Shift, Optional<Employee>> entry) {
		Shift shift = entry.getKey();
		Optional<Employee> employee = entry.getValue();
		return employee.map(e -> e.preferenceOf(shift))
				.orElseGet(() -> upperBound(shift));
	}

	private Rating upperBound(Shift shift) {
		return employees.stream()
				.filter(e -> e.hasCandidate(shift))
				.map(e -> e.preferenceOf(shift))
				.max(Rating::compareTo)
				.orElseThrow(IllegalStateException::new);
	}

	public static class AssignmentBuilder {

		private Map<Shift, Optional<Employee>> assignmentMap = new HashMap<>();

		private final Set<Employee> employees;

		public AssignmentBuilder(Set<Employee> employees) {
			this.employees = new HashSet<>(employees);
		}

		public AssignmentBuilder(Assignment assignment) {
			this.assignmentMap.putAll(assignment.assignmentMap);
			this.employees = new HashSet<>(assignment.employees);
		}

		public void init(Set<Shift> shifts) {
			shifts.forEach(shift -> assignmentMap.put(shift, Optional.empty()));
		}

		public void assign(Shift shift, Employee employee) {
			assert employees.contains(employee);
			assignmentMap.put(shift, Optional.ofNullable(employee));
		}

		public synchronized Assignment build() {
			assert assignmentMap != null;
			Assignment assignment = new Assignment(assignmentMap, employees);
			assignmentMap = null;
			return assignment;
		}

	}


}
