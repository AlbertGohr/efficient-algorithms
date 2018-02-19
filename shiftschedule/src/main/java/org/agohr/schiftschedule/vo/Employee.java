package org.agohr.schiftschedule.vo;

import lombok.*;


@Value
@Builder
@AllArgsConstructor
public class Employee {

	private long id;

	private String name;

	private int claimedNumberOfAssignments;

	@Getter(AccessLevel.NONE)
	private Preferences preferences;

	public boolean hasCandidate(Shift shift) {
		return preferences.containsCandidate(shift);
	}

	public Rating preferenceOf(Shift shift) {
		return preferences.preference(shift);
	}

	public String toString() {
		return "Employee(id=" + id + ", name="+name+")";
	}
}
