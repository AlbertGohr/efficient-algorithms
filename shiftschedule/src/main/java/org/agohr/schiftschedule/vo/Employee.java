package org.agohr.schiftschedule.vo;

import java.util.Collection;

import lombok.*;


@Value
@Builder
public class Employee {

	private long id;

	private String name;

	private int claimedNumberOfAssignments;

	@Getter(AccessLevel.NONE)
	@Singular
	private Collection<Shift> candidates;

	@Getter(AccessLevel.NONE)
	private Preferences preferences;

	public boolean hasCandidate(Shift shift) {
		return candidates.contains(shift);
	}

	public Rating preferenceOf(Shift shift) {
		return preferences.preference(shift);
	}

	public String toString() {
		return "Employee(id=" + id + ", name="+name+")";
	}
}
