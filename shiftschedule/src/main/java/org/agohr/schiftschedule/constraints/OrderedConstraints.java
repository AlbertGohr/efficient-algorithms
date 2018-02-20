package org.agohr.schiftschedule.constraints;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import org.agohr.schiftschedule.Constraint;
import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.Shift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class OrderedConstraints {

	@Getter(AccessLevel.NONE)
	private final List<Constraint> constraints;

	public OrderedConstraints(Constraint... constraints) {
		this(Arrays.stream(constraints).collect(Collectors.toList()));
	}

	public OrderedConstraints(List<Constraint> constraints) {
		this.constraints = Collections.unmodifiableList(new ArrayList<>(constraints));
	}

	public OrderedConstraints(Constraints[] values) {
		this(Arrays.stream(values)
				.map(Constraints::get)
				.collect(Collectors.toList()));
	}

	public Stream<Constraint> stream() {
		return constraints.stream();
	}

	public boolean anyViolated(Assignment assignment, Shift shift, Employee employee) {
		return constraints.stream()
				.anyMatch(constraint -> constraint.violated(assignment, shift, employee));
	}

}
