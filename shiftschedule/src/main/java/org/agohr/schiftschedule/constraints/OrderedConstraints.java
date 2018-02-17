package org.agohr.schiftschedule.constraints;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import org.agohr.schiftschedule.Constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Value
public class OrderedConstraints {

	@Getter(AccessLevel.NONE)
	private final List<Constraint> constraints;

	public OrderedConstraints(List<Constraint> constraints) {
		this.constraints = Collections.unmodifiableList(new ArrayList<>(constraints));
	}

	public Stream<Constraint> stream() {
		return constraints.stream();
	}

}
