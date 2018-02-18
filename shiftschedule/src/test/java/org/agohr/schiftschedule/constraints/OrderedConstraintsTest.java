package org.agohr.schiftschedule.constraints;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;

public class OrderedConstraintsTest {

	@Test
	public void testEmptyListNotViolated() {
		OrderedConstraints constraints = new OrderedConstraints(Collections.emptyList());
		boolean anyViolated = constraints.anyViolated(null, null, null);
		assertFalse(anyViolated);
	}

}