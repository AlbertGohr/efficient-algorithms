package org.agohr.schiftschedule;

import lombok.Value;
import org.agohr.schiftschedule.vo.Assignment;

@Value
class AssignmentQuality {

	// nullable
	private final Assignment assignment;

	private final int quality;

	public boolean isPresent() {
		return assignment != null;
	}
}
