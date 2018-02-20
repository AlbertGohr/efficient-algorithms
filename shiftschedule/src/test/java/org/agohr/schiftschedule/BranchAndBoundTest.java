package org.agohr.schiftschedule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.agohr.schiftschedule.constraints.Constraints;
import org.agohr.schiftschedule.constraints.OrderedConstraints;
import org.agohr.schiftschedule.upperbounds.CandidateUpperBoundStrategy;
import org.agohr.schiftschedule.upperbounds.SimpleUpperBoundStrategy;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.EmployeeFactory;
import org.agohr.schiftschedule.vo.Employees;
import org.agohr.schiftschedule.vo.Preferences;
import org.agohr.schiftschedule.vo.PreferencesFactory;
import org.agohr.schiftschedule.vo.Shift;
import org.agohr.schiftschedule.vo.ShiftFactory;
import org.agohr.schiftschedule.vo.Shifts;
import org.junit.Test;

public class BranchAndBoundTest {

	@Test public void testWithoutShifts() {
		// given
		Shifts shifts = new Shifts();
		Employee employee1 = EmployeeFactory.getEmployee(1L, "agohr", shifts);
		Employee employee2 = EmployeeFactory.getEmployee(2L, "bgohr", shifts);
		Employees employees = new Employees(employee1, employee2);
		ExpireCheck expireCheck = new ExpireCheck(30L * 1000L);
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

	@Test public void testUnsolvable() {
		// given
		Shift shift = ShiftFactory.getEarlyShiftByDay(1);
		Shifts shifts = new Shifts(shift);
		Employees noEmployees = new Employees();
		ExpireCheck expireCheck = new ExpireCheck(30L * 1000L);
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

	@Test public void testWithoutConstraints() {
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
		ExpireCheck expireCheck = new ExpireCheck(30L * 1000L);
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

	@Test public void testComplexScenario() {
		// given
		Set<Shift> shiftSet = initShifts();
		Shifts shifts = new Shifts(shiftSet);
		Employees employees = initEmployees(shiftSet);
		OrderedConstraints orderedConstraints = new OrderedConstraints(Constraints.values());
		ExpireCheck expireCheck = new ExpireCheck(30L * 1000L);
		UpperBoundStrategy upperBoundStrategy = new CandidateUpperBoundStrategy(employees);
		// TODO new ConstraintsAwareUpperBoundStrategy(employees,orderedConstraints);
		Configuration conf = new Configuration(orderedConstraints, upperBoundStrategy, expireCheck);
		Data data = new Data(employees, shifts);
		// when
		BranchAndBound bAndB = new BranchAndBound(conf, data);
		Optional<Map<Shift, Employee>> optAssignment = bAndB.compute();
		// then
		assertTrue(optAssignment.isPresent());
		Map<Shift, Employee> assignment = optAssignment.get();
		assertEquals(20, assignment.size());
		// TODO assert more details
	}

	private Set<Shift> initShifts() {
		Set<Shift> shiftSet = new HashSet<>();
		int firstMondayOfMonth = 5;
		for (int week = 0; week < 2; ++week) {
			for (int weekDay = 0; weekDay < 5; ++weekDay) {
				Shift early = ShiftFactory.getEarlyShiftByDay(firstMondayOfMonth + weekDay + week * 7);
				shiftSet.add(early);
				Shift late = ShiftFactory.getLateShiftByDay(firstMondayOfMonth + weekDay + week * 7);
				shiftSet.add(late);
			}
		}
		return shiftSet;
	}

	private Employees initEmployees(Set<Shift> shifts) {
		// agohr: works both weeks. doesn't like monday early or friday late (rating=1).
		Preferences prefAgohr = PreferencesFactory.getPreferences(shifts, this::ratingAgohr);
		Employee agohr = EmployeeFactory.getEmployee(1L, "agohr", prefAgohr);
		// bgohr: works first week. prefers early shifts. (rating=4)
		Set<Shift> firstWeek = shifts.stream().filter(this::isFirstWeek).collect(Collectors.toSet());
		Preferences prefBgohr = PreferencesFactory.getPreferences(firstWeek, shift -> isEarly(shift) ? 4 : 2);
		Employee bgohr = EmployeeFactory.getEmployee(2L, "bgohr", prefBgohr);
		// cgohr: works second week. prefers late shifts. (rating=4)
		Set<Shift> secondWeek = shifts.stream().filter(s -> !isFirstWeek(s)).collect(Collectors.toSet());
		Preferences prefCgohr = PreferencesFactory.getPreferences(secondWeek, shift -> isEarly(shift) ? 2 : 4);
		Employee cgohr = EmployeeFactory.getEmployee(3L, "cgohr", prefCgohr);
		return new Employees(agohr, bgohr, cgohr);
	}

	private int ratingAgohr(Shift shift) {
		switch (shift.getStart().getDayOfWeek()) {
			case MONDAY:
				return isEarly(shift) ? 1 : 5;
			case FRIDAY:
				return isEarly(shift) ? 5 : 1;
			default:
				return 3;
		}
	}

	/**
	 * early shift starts at 8.
	 */
	private boolean isEarly(Shift shift) {
		return shift.getStart().getHour() == 8;
	}

	/**
	 * first week ends with sunday 11 Feb 2018.
	 */
	private boolean isFirstWeek(Shift s) {
		return s.getStart().getDayOfMonth() <= 11;
	}

}
