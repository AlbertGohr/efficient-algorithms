package org.agohr.schiftschedule.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;


@Value
@Builder
public class Employee {

	private long id;

	private String name;

	private int claimedNumberOfAssignments;

	@Getter(AccessLevel.NONE)
	private Shifts candidates;

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
