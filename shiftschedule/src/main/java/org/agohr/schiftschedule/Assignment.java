package org.agohr.schiftschedule;

import lombok.Value;
import org.agohr.schiftschedule.vo.*;

import java.util.*;

@Value
public class Assignment {

	private final Map<Shift, Optional<Employee>> assignmentMap;

	private final Employees employees;

	private final UpperBoundStrategy upperBoundStrategy;

	private final int quality;

	private Assignment(Map<Shift, Optional<Employee>> assignmentMap, Employees employees, UpperBoundStrategy upperBoundStrategy) {
		this.assignmentMap = Collections.unmodifiableMap(assignmentMap);
		this.employees = employees;
		this.upperBoundStrategy = upperBoundStrategy;
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
				.orElseGet(() ->  upperBoundStrategy.getUpperBound(shift,this));
	}

	public Assignment assign(Shift shift, Employee employee) {
		Assignment.AssignmentBuilder builder = new Assignment.AssignmentBuilder(this);
		builder.assign(shift, employee);
		return builder.build();
	}

	public static class AssignmentBuilder {

		private Map<Shift, Optional<Employee>> assignmentMap = new HashMap<>();

		private final Employees employees;

		private final UpperBoundStrategy upperBoundStrategy;

		public AssignmentBuilder(Employees employees, UpperBoundStrategy upperBoundStrategy) {
			this.employees = employees;
			this.upperBoundStrategy = upperBoundStrategy;
		}

		public AssignmentBuilder(Assignment assignment) {
			this.assignmentMap.putAll(assignment.assignmentMap);
			this.employees = assignment.employees;
			this.upperBoundStrategy = assignment.upperBoundStrategy;
		}

		public void init(Shifts shifts) {
			shifts.stream().forEach(shift -> assignmentMap.put(shift, Optional.empty()));
		}

		public void assign(Shift shift, Employee employee) {
			assert employees.contains(employee);
			assignmentMap.put(shift, Optional.ofNullable(employee));
		}

		public synchronized Assignment build() {
			assert assignmentMap != null;
			Assignment assignment = new Assignment(assignmentMap, employees, upperBoundStrategy);
			assignmentMap = null;
			return assignment;
		}

	}


}
