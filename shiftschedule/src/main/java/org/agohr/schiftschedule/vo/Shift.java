package org.agohr.schiftschedule.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Shift {

	private long id;

	@Getter(AccessLevel.NONE)
	private TimeSlice timeSlice;

	public LocalDateTime getStart() {
		return timeSlice.getStart();
	}

	public boolean overlap(Shift other) {
		return timeSlice.overlap(other.timeSlice);
	}

}
