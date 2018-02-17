package org.agohr.schiftschedule.vo;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Shifts {

	@Getter(AccessLevel.NONE)
	private final Set<Shift> shifts;

	public Shifts(Set<Shift> shifts) {
		this.shifts = Collections.unmodifiableSet(new HashSet<>(shifts));
	}

	public Stream<Shift> stream() {
		return shifts.stream();
	}

}