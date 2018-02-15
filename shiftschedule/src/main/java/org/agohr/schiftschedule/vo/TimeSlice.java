package org.agohr.schiftschedule.vo;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class TimeSlice {

	private LocalDateTime start;

	private LocalDateTime stop;

	public boolean overlap(TimeSlice other) {
		return contains(other.start) || contains(other.stop);
	}

	private boolean contains(LocalDateTime dateTime) {
		return (start.isBefore(dateTime) || start.equals(dateTime)) && (dateTime.isBefore(stop) || dateTime.equals(stop));
	}

}
