package org.agohr.schiftschedule.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class Shifts {

	@Getter(AccessLevel.NONE)
	private final Set<Shift> shifts;

	public Shifts(Shift... shifts) {
		this(Arrays.stream(shifts).collect(Collectors.toSet()));
	}

	public Shifts(Set<Shift> shifts) {
		this.shifts = Collections.unmodifiableSet(new HashSet<>(shifts));
	}

	public Shifts(Shifts other) {
		this(other.shifts);
	}

	public Stream<Shift> stream() {
		return shifts.stream();
	}

	public boolean contains(Shift shift) {
		return shifts.contains(shift);
	}

	public boolean isEmpty() {
		return shifts.isEmpty();
	}

	public int size() {
		return shifts.size();
	}
}