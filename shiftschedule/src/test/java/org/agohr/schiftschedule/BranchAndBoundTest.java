package org.agohr.schiftschedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.agohr.schiftschedule.constraints.Constraints;
import org.agohr.schiftschedule.vo.Assignment;
import org.agohr.schiftschedule.vo.Employee;
import org.agohr.schiftschedule.vo.OptionalAssignment;
import org.agohr.schiftschedule.vo.Preferences;
import org.agohr.schiftschedule.vo.Rating;
import org.agohr.schiftschedule.vo.Shift;
import org.agohr.schiftschedule.vo.TimeSlice;
import org.junit.Test;

public class BranchAndBoundTest {

	@Test
	public void testComputeExpectAssignment() {
		// given
		Set<Shift> shifts = new HashSet<>();
		for (int j = 0; j < 3; ++j) {
			for (int i = 0; i < 5; ++i) {
				for (int k = 0; k < 2; ++k) {
					LocalDateTime start = LocalDateTime.of(2018, Month.JANUARY, 8 + i + j * 7, 8 + k, 0);
					LocalDateTime stop = LocalDateTime.of(2018, Month.JANUARY, 8 + i + j * 7, 17 + k, 0);
					TimeSlice timeSlice = new TimeSlice(start, stop);
					Shift shift = new Shift(1L, timeSlice);
					shifts.add(shift);
				}
			}
		}
		Collection<Shift> candidates = new HashSet<>(shifts);
		Map<Shift, Rating> p = new HashMap<>();
		// TODO restrict candidates, vary preferences
		for (Shift s : candidates) {
			p.put(s, new Rating(3));
		}
		Preferences preferences = new Preferences(p);
		Set<Employee> employees = new HashSet<>();
		for (int i = 0; i < 3; ++i) {
			Employee employee = Employee.builder()
					.id(i)
					.name("agohr" + i)
					.claimedNumberOfAssignments(10) // TODO vary
					.candidates(candidates)
					.preferences(preferences)
					.build();
			employees.add(employee);
		}
		List<Constraint> constraints = new ArrayList<>();
		Arrays.stream(Constraints.values())
				.map(Constraints::get)
				.forEach(constraints::add);
		// when
		BranchAndBound bAndB = new BranchAndBound(constraints, employees, shifts);
		OptionalAssignment optAssignment = bAndB.compute(10L * 1000L);
		// then
		assertTrue(optAssignment.isPresent());
		Assignment assignment = optAssignment.getAssignment();
		Set<Shift> keys = assignment.keys();
		assertEquals(30, keys.size());
		// TODO assert
/*		Shift key = keys.iterator().next();
		assertEquals(shift, key);
		Optional<Employee> assignedEmployee = assignment.get(key);
		assertTrue(assignedEmployee.isPresent());
		assertEquals(employee, assignedEmployee.get());*/
	}

}
