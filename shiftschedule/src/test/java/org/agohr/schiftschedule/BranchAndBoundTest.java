package org.agohr.schiftschedule;

import org.agohr.schiftschedule.constraints.OrderedConstraints;
import org.agohr.schiftschedule.expirecheck.ExpireCheckImpl;
import org.agohr.schiftschedule.upperbounds.SimpleUpperBoundStrategy;
import org.agohr.schiftschedule.vo.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BranchAndBoundTest {

	@Test
	public void testWithoutShifts() {
		// given
		Shifts shifts = new Shifts();
		Employee employee1 = EmployeeFactory.getEmployee(1L, "agohr", shifts);
		Employee employee2 = EmployeeFactory.getEmployee(2L, "bgohr", shifts);
		Employees employees = new Employees(employee1, employee2);
		ExpireCheck expireCheck = new ExpireCheckImpl(30L * 1000L);
		UpperBoundStrategy upperBoundStrategy = new SimpleUpperBoundStrategy();
		OrderedConstraints noConstraints = new OrderedConstraints();
		Configuration conf = new Configuration(noConstraints, upperBoundStrategy, expireCheck);
		Data data = new Data(employees, shifts);
		// when
		BranchAndBound bAndB = new BranchAndBound(conf, data);
		Optional<Map<Shift, Employee>> result = bAndB.compute();
		// then
		assertTrue(result.isPresent());
		Map<Shift, Employee> assignment = result.get();
		assertTrue(assignment.isEmpty());
	}

	@Test
	public void testUnsolvable() {
		// given
		Shift shift = ShiftFactory.getEarlyShiftByDay(1);
		Shifts shifts = new Shifts(shift);
		Employees noEmployees = new Employees();
		ExpireCheck expireCheck = new ExpireCheckImpl(30L * 1000L);
		UpperBoundStrategy upperBoundStrategy = new SimpleUpperBoundStrategy();
		OrderedConstraints noConstraints = new OrderedConstraints();
		Configuration conf = new Configuration(noConstraints, upperBoundStrategy, expireCheck);
		Data data = new Data(noEmployees, shifts);
		// when
		BranchAndBound bAndB = new BranchAndBound(conf, data);
		Optional<Map<Shift, Employee>> result = bAndB.compute();
		// then
		assertFalse(result.isPresent());
	}

	@Test
	public void testWithoutConstraints() {
		// given
		Shift shift1 = ShiftFactory.getEarlyShiftByDay(1);
		Shift shift2 = ShiftFactory.getEarlyShiftByDay(2);
		List<Shift> shiftList = Arrays.asList(shift1, shift2);
		Shifts shifts = new Shifts(shiftList);
		Preferences pre1 = PreferencesFactory.getPreferences(shiftList, 2, 4);
		Employee employee1 = EmployeeFactory.getEmployee(1L, "agohr", pre1);
		Preferences pre2 = PreferencesFactory.getDefaultPreferences(shifts);
		Employee employee2 = EmployeeFactory.getEmployee(2L, "bgohr", pre2);
		Employees employees = new Employees(employee1, employee2);
		ExpireCheck expireCheck = new ExpireCheckImpl(30L * 1000L);
		UpperBoundStrategy upperBoundStrategy = new SimpleUpperBoundStrategy();
		OrderedConstraints noConstraints = new OrderedConstraints();
		Configuration conf = new Configuration(noConstraints, upperBoundStrategy, expireCheck);
		Data data = new Data(employees, shifts);
		// when
		BranchAndBound bAndB = new BranchAndBound(conf, data);
		Optional<Map<Shift, Employee>> result = bAndB.compute();
		// then
		assertTrue(result.isPresent());
		Map<Shift, Employee> assignment = result.get();
		assertEquals(2, assignment.size());
		assertThat(assignment.get(shift1), is(employee2));
		assertThat(assignment.get(shift2), is(employee1));
	}

}
