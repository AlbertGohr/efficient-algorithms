package org.agohr.schiftschedule;

import org.agohr.schiftschedule.constraints.Constraints;
import org.agohr.schiftschedule.constraints.OrderedConstraints;
import org.agohr.schiftschedule.expirecheck.ExpireCheckImpl;
import org.agohr.schiftschedule.upperbounds.ConstraintsAwareUpperBoundStrategy;
import org.agohr.schiftschedule.vo.*;
import org.junit.Test;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BranchAndBoundComplexTest {

	@Test
	public void testComplexScenario() {
		// given
		Set<Shift> shiftSet = initShifts();
		Shifts shifts = new Shifts(shiftSet);
		Employees employees = initEmployees(shiftSet);
		OrderedConstraints orderedConstraints = new OrderedConstraints(Constraints.values());
		ExpireCheck expireCheck = new ExpireCheckImpl(30L * 1000L);
		UpperBoundStrategy upperBoundStrategy = new ConstraintsAwareUpperBoundStrategy(employees, orderedConstraints);
		Configuration conf = new Configuration(orderedConstraints, upperBoundStrategy, expireCheck);
		Data data = new Data(employees, shifts);
		// when
		BranchAndBound bAndB = new BranchAndBound(conf, data);
		Optional<Map<Shift, Employee>> optAssignment = bAndB.compute();
		// then
		assertTrue(optAssignment.isPresent());
		Map<Shift, Employee> assignment = optAssignment.get();
		assertEquals(20, assignment.size());
		for (Shift shift : assignment.keySet()) {
			assertEquals(shift.toString(), expected(shift), assignment.get(shift).getName());
		}
	}

	/**
	 * two weeks consisting of early and late shifts.
	 */
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

	/**
	 * agohr, bgohr, cgohr.
	 */
	private Employees initEmployees(Set<Shift> shifts) {
		// agohr: works both weeks. doesn't like monday early and friday late (rating=1).
		Preferences prefAgohr = PreferencesFactory.getPreferences(shifts, this::ratingAgohr);
		Employee agohr = new Employee(1L, "agohr", 10, prefAgohr);
		// bgohr: works first week. prefers early shifts. (rating=4)
		Set<Shift> firstWeek = shifts.stream().filter(this::isFirstWeek).collect(Collectors.toSet());
		Preferences prefBgohr = PreferencesFactory.getPreferences(firstWeek, shift -> isEarly(shift) ? 4 : 2);
		Employee bgohr = new Employee(2L, "bgohr", 5, prefBgohr);
		// cgohr: works second week. prefers late shifts. (rating=4)
		Set<Shift> secondWeek = shifts.stream().filter(s -> !isFirstWeek(s)).collect(Collectors.toSet());
		Preferences prefCgohr = PreferencesFactory.getPreferences(secondWeek, shift -> isEarly(shift) ? 2 : 4);
		Employee cgohr = new Employee(3L, "cgohr", 5, prefCgohr);
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

	/**
	 * agohr wins always with pref 5. <br/>
	 * bgohr and cgohr win otherwise wit pref 4. <br/>
	 * remaining days are filled by agohr.
	 */
	private String expected(Shift shift) {
		// pref 5
		if (DayOfWeek.MONDAY.equals(shift.getStart().getDayOfWeek())) {
			if (!isEarly(shift)) {
				return "agohr";
			}
			return isFirstWeek(shift) ? "bgohr" : "cgohr";
		}
		if (DayOfWeek.FRIDAY.equals(shift.getStart().getDayOfWeek())) {
			if (isEarly(shift)) {
				return "agohr";
			}
			return isFirstWeek(shift) ? "bgohr" : "cgohr";
		}
		// pref 4
		if (isFirstWeek(shift) && isEarly(shift)) {
			return "bgohr";
		}
		if (!isFirstWeek(shift) && !isEarly(shift)) {
			return "cgohr";
		}
		// remaining
		return "agohr";
	}

}
