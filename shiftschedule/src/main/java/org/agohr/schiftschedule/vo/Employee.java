package org.agohr.schiftschedule.vo;

import java.util.Collection;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;


@Value
@Builder
public class Employee {

	private long id;

	private String name;

	private int claimedNumberOfAssignments;

	@Singular
	private Collection<Shift> candidates;

	private Preferences preferences;

	public String toString() {
		return "Employee(id=" + id + ", name="+name+")";
	}
}
