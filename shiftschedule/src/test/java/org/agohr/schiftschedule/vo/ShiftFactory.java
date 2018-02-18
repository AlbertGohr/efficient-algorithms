package org.agohr.schiftschedule.vo;

import java.time.LocalDateTime;
import java.time.Month;

public class ShiftFactory {

	public static Shift getShiftByDay(int day) {
		LocalDateTime start = LocalDateTime.of(2018, Month.FEBRUARY, day, 8, 0);
		LocalDateTime stop = LocalDateTime.of(2018, Month.FEBRUARY, day, 17, 0);
		TimeSlice timeSlice = new TimeSlice(start, stop);
		return new Shift(1L, timeSlice);
	}

}
