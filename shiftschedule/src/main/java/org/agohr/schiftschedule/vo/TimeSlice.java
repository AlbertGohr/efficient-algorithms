package org.agohr.schiftschedule.vo;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Value
@AllArgsConstructor
public class TimeSlice {

	private LocalDateTime start;

	private LocalDateTime stop;

	public TimeSlice(LocalDate date, LocalTime startTime, LocalTime stopTime) {
		start = LocalDateTime.of(date, startTime);
		stop = LocalDateTime.of(date, stopTime);
	}

	public boolean overlap(TimeSlice other) {
		return contains(other.start) || contains(other.stop);
	}

	private boolean contains(LocalDateTime dateTime) {
		return (start.isBefore(dateTime) || start.equals(dateTime)) && (dateTime.isBefore(stop) || dateTime.equals(stop));
	}

}
