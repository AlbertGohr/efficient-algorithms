package org.agohr.schiftschedule.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Value
@Builder
public class Shifts {

	@Getter(AccessLevel.NONE)
	private final Set<Shift> shifts;

	public Shifts(Set<Shift> shifts) {
		this.shifts = Collections.unmodifiableSet(new HashSet<>(shifts));
	}

	public Stream<Shift> stream() {
		return shifts.stream();
	}

	public boolean contains(Shift shift) {
		return shifts.contains(shift);
	}
}