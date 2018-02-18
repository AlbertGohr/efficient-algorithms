package org.agohr.schiftschedule;

import org.agohr.schiftschedule.constraints.Constraints;
import org.agohr.schiftschedule.constraints.OrderedConstraints;
import org.agohr.schiftschedule.upperbounds.CandidateUpperBoundStrategy;
import org.agohr.schiftschedule.vo.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.Assert.assertTrue;

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
					Shift shift = new Shift(j * 100 + i * 10 + k, timeSlice);
					shiftSet.add(shift);
				}
			}
		}
		Shifts shifts = new Shifts(shiftSet);
		Set<Shift> candidatesSet = new HashSet<>(shiftSet);
		Map<Shift, Rating> p = new HashMap<>();
		// TODO restrict candidates, vary preferences
		int j = 3;
		for (Shift s : candidatesSet) {
			p.put(s, new Rating(j));
			j = j % 5 + 1;
		}
		Shifts candidates = new Shifts(candidatesSet);
		Preferences preferences = new Preferences(p);
		Set<Employee> employeesSet = new HashSet<>();
		for (int i = 0; i < 3; ++i) {
			Employee employee = Employee.builder()
					.id(i)
					.name("agohr" + i)
					.claimedNumberOfAssignments(10) // TODO vary
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
		Configuration conf = new Configuration(orderedConstraints, upperBoundStrategy, expireCheck);
		Data data = new Data(employees, shifts);
		// when
		BranchAndBound bAndB = new BranchAndBound(conf, data);
		Optional<Assignment> optAssignment = bAndB.compute();
		// then
		assertTrue(optAssignment.isPresent());
		Assignment assignment = optAssignment.get();
		// TODO assert
		//Set<Shift> keys = assignment.getAssignment().keySet();
		//assertEquals(30, keys.size());
/*		Shift key = keys.iterator().next();
		assertEquals(shift, key);
		Optional<Employee> assignedEmployee = assignment.get(key);
		assertTrue(assignedEmployee.isPresent());
		assertEquals(employee, assignedEmployee.get());*/
	}

}
