package org.agohr.schiftschedule.constraints;

import org.agohr.schiftschedule.Constraint;
import org.agohr.schiftschedule.vo.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CandidateConstraintTest {

	private final Constraint constraint = new CandidateConstraint();

	private Shift shift1; // candidate
	private Shift shift2; // no candidate
	private Employee employee;
	private Assignment assignment;

	@Before
	public void before() {
		shift1 = ShiftFactory.getEarlyShiftByDay(1);
		shift2 = ShiftFactory.getEarlyShiftByDay(2);
		Shifts shifts = new Shifts(shift1, shift2);
		Shifts candidates = new Shifts(shift1);
		employee = EmployeeFactory.getEmployee(1L, "agohr", candidates);
		assignment = new Assignment(shifts);
	}

	@Test
	public void testNotViolated() {
		boolean violated = constraint.violated(assignment, shift1, employee);
		assertFalse(violated);
	}

	@Test
	public void testViolated() {
		boolean violated = constraint.violated(assignment, shift2, employee);
		assertTrue(violated);
	}

}