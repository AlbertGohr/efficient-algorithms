package org.agohr.schiftschedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import org.agohr.schiftschedule.constraints.Constraints;
import org.agohr.schiftschedule.constraints.OrderedConstraints;
import org.agohr.schiftschedule.upperbounds.CandidateUpperBoundStrategy;
import org.agohr.schiftschedule.vo.*;
import org.junit.Test;

public class BranchAndBoundTest {

	@Test
	public void testComputeExpectAssignment() {
		// given
		Set<Shift> shiftSet = new HashSet<>();
		for (int j = 0; j < 3; ++j) {
			for (int i = 0; i < 5; ++i) {
				for (int k = 0; k < 2; ++k) {
					LocalDateTime start = LocalDateTime.of(2018, Month.JANUARY, 8 + i + j * 7, 8 + k, 0);
					LocalDateTime stop = LocalDateTime.of(2018, Month.JANUARY, 8 + i + j * 7, 17 + k, 0);
					TimeSlice timeSlice = new TimeSlice(start, stop);
					Shift shift = new Shift(1L, timeSlice);
					shiftSet.add(shift);
				}
			}
		}
		Shifts shifts = new Shifts(shiftSet);
		Collection<Shift> candidates = new HashSet<>(shiftSet);
		Map<Shift, Rating> p = new HashMap<>();
		// TODO restrict candidates, vary preferences
		for (Shift s : candidates) {
			p.put(s, new Rating(3));
		}
		Preferences preferences = new Preferences(p);
		Set<Employee> employeesSet = new HashSet<>();
		for (int i = 0; i < 3; ++i) {
			Employee employee = Employee.builder()
					.id(i)
					.name("agohr" + i)
					.claimedNumberOfAssignments(10) // TODO vary
					.candidates(candidates)
					.preferences(preferences)
					.build();
			employeesSet.add(employee);
		}
		Employees employees = new Employees(employeesSet);
		List<Constraint> constraintsList = new ArrayList<>();
		Arrays.stream(Constraints.values())
				.map(Constraints::get)
				.forEach(constraintsList::add);
		OrderedConstraints orderedConstraints = new OrderedConstraints(constraintsList);
		ExpireCheck expireCheck = new ExpireCheck(30L * 1000L);
		UpperBoundStrategy upperBoundStrategy = new CandidateUpperBoundStrategy(employees);
		// when
		BranchAndBoundConfiguration conf = new BranchAndBoundConfiguration(orderedConstraints, upperBoundStrategy, expireCheck, employees, shifts);
		BranchAndBound bAndB = new BranchAndBound(conf);
		Optional<Assignment> optAssignment = bAndB.compute();
		// then
		assertTrue(optAssignment.isPresent());
		Assignment assignment = optAssignment.get();
		Set<Shift> keys = assignment.getAssignment().keySet();
		assertEquals(30, keys.size());
		// TODO assert
/*		Shift key = keys.iterator().next();
		assertEquals(shift, key);
		Optional<Employee> assignedEmployee = assignment.get(key);
		assertTrue(assignedEmployee.isPresent());
		assertEquals(employee, assignedEmployee.get());*/
	}

}
