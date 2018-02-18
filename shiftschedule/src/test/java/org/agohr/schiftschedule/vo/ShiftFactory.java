package org.agohr.schiftschedule.vo;

public class ShiftFactory {

	public static Shift getEarlyShiftByDay(int day) {
		TimeSlice timeSlice = TimeSliceFactory.getEarlyWorkdaySliceByDay(day);
		return new Shift(day, timeSlice);
	}

	public static Shift getLateShiftByDay(int day) {
		TimeSlice timeSlice = TimeSliceFactory.getLateWorkdaySliceByDay(day);
		return new Shift(day, timeSlice);
	}

}
