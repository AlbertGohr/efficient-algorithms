package org.agohr.schiftschedule.constraints;

import org.agohr.schiftschedule.Constraint;
import org.agohr.schiftschedule.vo.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClaimedNumberOfAssignmentsConstraintTest {

	private final Constraint constraint = new ClaimedNumberOfAssignmentsConstraint();

	private Shift shift1;
	private Shift shift2;
	private Employee employee1;
	private Employee employee2;
	private Employee employee3;
	private Assignment assignment;

	@Before
	public void before() {
		shift1 = ShiftFactory.getEarlyShiftByDay(1);
		shift2 = ShiftFactory.getEarlyShiftByDay(2);
		Shifts shifts = new Shifts(shift1, shift2);
		Preferences preferences = PreferencesFactory.getDefaultPreferences(shifts);
		employee1 = new Employee(1L, "khell", 1, preferences);
		employee2 = new Employee(2L, "agohr", 2, preferences);
		employee3 = new Employee(3L, "bgohr", 3, preferences);
		assignment = new Assignment(shifts);
	}

	@Test
	public void testNotViolated() {
		assertFalse(constraint.violated(assignment, shift1, employee2));
		assignment = assignment.add(shift1, employee2);
		assertFalse(constraint.violated(assignment, shift2, employee2));
	}

	@Test
	public void testViolatedToManyAssigned() {
		assignment = assignment.add(shift1, employee1);
		boolean violated = constraint.violated(assignment, shift2, employee1);
		assertTrue(violated);
	}


	@Test
	public void testViolatedNotEnoughAvailable() {
		boolean violated = constraint.violated(assignment, shift1, employee3);
		assertTrue(violated);
	}

}