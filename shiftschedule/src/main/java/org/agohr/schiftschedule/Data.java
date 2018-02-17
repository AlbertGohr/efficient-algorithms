package org.agohr.schiftschedule;

import lombok.Value;
import org.agohr.schiftschedule.vo.Employees;
import org.agohr.schiftschedule.vo.Shifts;

@Value
public class Data {

	private final Employees employees;
	private final Shifts shifts;

}
