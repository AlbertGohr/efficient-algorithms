package org.agohr.schiftschedule.vo;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimeSliceTest {

	@Test
	public void testOverlap() {
		LocalDate date = LocalDate.of(2018, Month.FEBRUARY, 1);
		TimeSlice timeSlice1 = new TimeSlice(date, LocalTime.of(8, 0), LocalTime.of(17, 0));
		TimeSlice timeSlice2 = new TimeSlice(date, LocalTime.of(9, 0), LocalTime.of(18, 0));
		boolean overlap = timeSlice1.overlap(timeSlice2);
		assertTrue(overlap);
	}

	@Test
	public void testNoOverlap() {
		LocalTime startTime = LocalTime.of(8, 0);
		LocalTime stopTime = LocalTime.of(17, 0);
		TimeSlice timeSlice1 = new TimeSlice(LocalDate.of(2018, Month.FEBRUARY, 1), startTime, stopTime);
		TimeSlice timeSlice2 = new TimeSlice(LocalDate.of(2018, Month.FEBRUARY, 2), startTime, stopTime);
		boolean overlap = timeSlice1.overlap(timeSlice2);
		assertFalse(overlap);
	}

}