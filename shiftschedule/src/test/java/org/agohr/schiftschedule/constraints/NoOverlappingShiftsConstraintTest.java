package org.agohr.schiftschedule.constraints;

import org.agohr.schiftschedule.Constraint;
import org.agohr.schiftschedule.vo.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NoOverlappingShiftsConstraintTest {

	private final Constraint constraint = new NoOverlappingShiftsConstraint();

	private Shift shiftDay1Late;
	private Shift shiftDay2;
	private Employee employee;
	private Assignment assignment;

	@Before
	public void before() {
		Shift shiftDay1Early = ShiftFactory.getEarlyShiftByDay(1);
		shiftDay1Late = ShiftFactory.getLateShiftByDay(1);
		shiftDay2 = ShiftFactory.getEarlyShiftByDay(2);
		Shifts shifts = new Shifts(shiftDay1Early, shiftDay1Late, shiftDay2);
		Shifts candidates = new Shifts(shifts);
		employee = EmployeeFactory.getEmployee(candidates);
		assignment = new Assignment(shifts).add(shiftDay1Early, employee);
	}

	@Test
	public void testNotViolated() {
		boolean violated = constraint.violated(assignment, shiftDay2, employee);
		assertFalse(violated);
	}

	@Test
	public void testViolated() {
		boolean violated = constraint.violated(assignment, shiftDay1Late, employee);
		assertTrue(violated);
	}

}