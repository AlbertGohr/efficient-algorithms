package org.agohr.schiftschedule.constraints;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.agohr.schiftschedule.Constraint;
import org.junit.Before;
import org.junit.Test;

public class OrderedConstraintsTest {

	private Constraint constraint1 = mock(Constraint.class);
	private Constraint constraint2 = mock(Constraint.class);
	private Constraint constraint3 = mock(Constraint.class);

	@Before public void before() {
		when(constraint1.violated(anyObject(), anyObject(), anyObject())).thenReturn(false);
		when(constraint2.violated(anyObject(), anyObject(), anyObject())).thenReturn(false);
		when(constraint3.violated(anyObject(), anyObject(), anyObject())).thenReturn(true);
	}

	@Test public void testEmptyListNotViolated() {
		List<Constraint> constraintList = Collections.emptyList();
		OrderedConstraints constraints = new OrderedConstraints(constraintList);
		boolean anyViolated = constraints.anyViolated(null, null, null);
		assertFalse(anyViolated);
	}

	@Test public void testNotViolated() {
		List<Constraint> constraintList = Arrays.asList(constraint1, constraint2);
		OrderedConstraints constraints = new OrderedConstraints(constraintList);
		boolean anyViolated = constraints.anyViolated(null, null, null);
		assertFalse(anyViolated);
	}

	@Test public void testViolated() {
		List<Constraint> constraintList = Arrays.asList(constraint1, constraint2, constraint3);
		OrderedConstraints constraints = new OrderedConstraints(constraintList);
		boolean anyViolated = constraints.anyViolated(null, null, null);
		assertTrue(anyViolated);
	}

}