package org.agohr.schiftschedule.upperbounds;

import org.agohr.schiftschedule.UpperBoundStrategy;
import org.agohr.schiftschedule.vo.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CandidateUpperBoundStrategyTest {

	private Shift shift;
	private Employee employee;
	private Assignment assignment;

	@Before
	public void before() {
		shift = ShiftFactory.getEarlyShiftByDay(1);
		Shifts shifts = new Shifts(shift);
		Shifts candidates = new Shifts(shift);
		employee = EmployeeFactory.getEmployee(1L, "agohr", candidates);
		assignment = new Assignment(shifts);
	}

	@Test
	public void testUpperBound() {
		Employees employees = new Employees(employee);
		UpperBoundStrategy strategy = new CandidateUpperBoundStrategy(employees);

		Rating upperBound = strategy.getUpperBound(shift, assignment);

		assertEquals(Rating.avg, upperBound);
	}

}