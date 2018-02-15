package org.agohr.schiftschedule.vo;

import lombok.Value;

@Value
public class Shift {

	private long id;

	private TimeSlice timeSlice;

}
