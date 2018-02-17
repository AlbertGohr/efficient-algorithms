package org.agohr.schiftschedule;

import lombok.Value;
import org.agohr.schiftschedule.constraints.OrderedConstraints;

@Value
class Configuration {

	private final OrderedConstraints constraints;
	private final UpperBoundStrategy upperBoundStrategy;
	private final ExpireCheck expireCheck;

}
