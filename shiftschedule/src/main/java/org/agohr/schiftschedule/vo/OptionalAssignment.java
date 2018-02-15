package org.agohr.schiftschedule.vo;

import lombok.Value;

@Value
public class OptionalAssignment {

	private final Assignment assignment;

	public int getQuality() {
		if (assignment == null) {
			return Integer.MIN_VALUE;
		}
		return assignment.getQuality();
	}

	public boolean isPresent() {
		return assignment != null;
	}

}
