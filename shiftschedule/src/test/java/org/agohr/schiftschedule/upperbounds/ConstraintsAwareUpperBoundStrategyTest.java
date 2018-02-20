package org.agohr.schiftschedule.upperbounds;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.agohr.schiftschedule.Constraint;
import org.agohr.schiftschedule.constraints.OrderedConstraints;
import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.EmployeeFactory;
import org.agohr.schiftschedule.vo.Employees;
import org.agohr.schiftschedule.vo.Preferences;
import org.agohr.schiftschedule.vo.PreferencesFactory;
import org.agohr.schiftschedule.vo.Rating;
import org.agohr.schiftschedule.vo.Shift;
import org.agohr.schiftschedule.vo.ShiftFactory;
import org.agohr.schiftschedule.vo.Shifts;
import org.junit.Before;
import org.junit.Test;

public class ConstraintsAwareUpperBoundStrategyTest {

	private final Shift shift1 = ShiftFactory.getEarlyShiftByDay(1);
	private final Shift shift2 = ShiftFactory.getEarlyShiftByDay(2);

	private final Constraint constraint = mock(Constraint.class);

	private Employee employee1;
	private Employee employee2;
	private Assignment assignment;
	private ConstraintsAwareUpperBoundStrategy strategy;

	@Before public void before() {
		List<Shift> shiftList = Arrays.asList(shift1, shift2);
		Shifts shifts = new Shifts(shiftList);
		assignment = new Assignment(shifts);
		Preferences pref1 = PreferencesFactory.getPreferences(shiftList, 1, 5);
		Preferences pref2 = PreferencesFactory.getPreferences(shiftList, 2, 4);
		employee1 = EmployeeFactory.getEmployee(1L, "agohr", pref1);
		employee2 = EmployeeFactory.getEmployee(2L, "bgohr", pref2);
		Employees employees = new Employees(employee1, employee2);
		OrderedConstraints constraints = new OrderedConstraints(constraint);
		strategy = new ConstraintsAwareUpperBoundStrategy(employees, constraints);
	}

	@Test public void testUpperBound() {
		when(constraint.violated(anyObject(), anyObject(), eq(employee1))).thenReturn(false);
		when(constraint.violated(anyObject(), anyObject(), eq(employee2))).thenReturn(true);
		assertEquals(new Rating(1), strategy.getUpperBound(shift1, assignment));
		assertEquals(new Rating(5), strategy.getUpperBound(shift2, assignment));
	}

}