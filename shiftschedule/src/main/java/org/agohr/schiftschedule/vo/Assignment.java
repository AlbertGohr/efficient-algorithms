package org.agohr.schiftschedule.vo;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import lombok.Value;

@Value
public class Assignment {

	private final Map<Shift, Optional<Employee>> assignmentMap;

	private final int quality;

	private Assignment(Map<Shift, Optional<Employee>> assignmentMap) {
		this.assignmentMap = Collections.unmodifiableMap(assignmentMap);
		quality = computeQuality();
	}

	public Optional<Shift> getNextUnassigned() {
		return assignmentMap.entrySet().stream()
				.filter(entry -> !entry.getValue().isPresent())
				.map(Map.Entry::getKey)
				.min(Comparator.comparing(s -> s.getTimeSlice().getStart()));
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
		return employee.map(e -> e.getPreferences().preference(shift)).orElseGet(this::upperBound);
	}

	private Rating upperBound() {
		// TODO improvable
		return new Rating(Rating.max);
	}

	public static class AssignmentBuilder {

		private Map<Shift, Optional<Employee>> assignmentMap = new HashMap<>();

		public AssignmentBuilder() {
			// default constructor
		}

		public AssignmentBuilder(Assignment assignment) {
			this.assignmentMap.putAll(assignment.assignmentMap);
		}

		public void init(Set<Shift> shifts) {
			shifts.forEach(shift -> assignmentMap.put(shift, Optional.empty()));
		}

		public void assign(Shift shift, Employee employee) {
			assignmentMap.put(shift, Optional.ofNullable(employee));
		}

		public synchronized Assignment build() {
			if (assignmentMap == null) {
				throw new IllegalStateException("Assignment has already been build.");
			}
			Assignment assignment = new Assignment(assignmentMap);
			assignmentMap = null;
			return assignment;
		}

	}


}
