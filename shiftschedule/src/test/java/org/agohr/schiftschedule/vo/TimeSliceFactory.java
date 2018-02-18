package org.agohr.schiftschedule.vo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class TimeSliceFactory {

	public static TimeSlice getEarlyWorkdaySliceByDay(int day) {
		LocalDate date = LocalDate.of(2018, Month.FEBRUARY, day);
		LocalTime start = LocalTime.of(8, 0);
		LocalTime stop = LocalTime.of(17, 0);
		return new TimeSlice(date, start, stop);
	}

	public static TimeSlice getLateWorkdaySliceByDay(int day) {
		LocalDate date = LocalDate.of(2018, Month.FEBRUARY, day);
		LocalTime start = LocalTime.of(9, 0);
		LocalTime stop = LocalTime.of(18, 0);
		return new TimeSlice(date, start, stop);
	}


}
