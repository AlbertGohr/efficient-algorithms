package org.agohr.schiftschedule;

import lombok.Value;
import org.agohr.schiftschedule.constraints.OrderedConstraints;
import org.agohr.schiftschedule.vo.Employees;
import org.agohr.schiftschedule.vo.Shifts;

@Value
class BranchAndBoundConfiguration {

	private final OrderedConstraints constraints;
	private final UpperBoundStrategy upperBoundStrategy;
	private final ExpireCheck expireCheck;
	private final Employees employees;
	private final Shifts shifts;

}
