package org.agohr.schiftschedule.vo;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class AssignmentTest {

	private Assignment assignment;

	private Shifts shifts;

	private Employee employee;

	@Before
	public void before() {
		Set<Shift> shiftSet = new HashSet<>();
		for (int i = 9; i >= 5; --i) {
			Shift shift = ShiftFactory.getEarlyShiftByDay(i);
			shiftSet.add(shift);
		}
		shifts = new Shifts(shiftSet);
		Preferences preferences = PreferencesFactory.getDefaultPreferences(shifts);
		employee = EmployeeFactory.getEmployee(preferences);
		assignment = new Assignment(shifts);
	}

	@Test
	public void testEmpty() {
		Assignment assignment = new Assignment();
		Optional<Shift> next = assignment.getNextUnassignedShift();
		assertFalse(next.isPresent());
	}

	@Test
	public void testNoAssignments() {
		Optional<Shift> next = assignment.getNextUnassignedShift();
		assertTrue(next.isPresent());
		LocalDateTime start = next.get().getStart();
		assertEquals(5, start.getDayOfMonth());
	}

	@Test
	public void testSemiAssignments() {
		Shift shiftFirstDay = getShiftFirstDay();
		assignment = assignment.add(shiftFirstDay, employee);
		Optional<Shift> next = assignment.getNextUnassignedShift();
		assertTrue(next.isPresent());
		LocalDateTime start = next.get().getStart();
		assertEquals(6, start.getDayOfMonth());
	}

	private Shift getShiftFirstDay() {
		return shifts.stream()
				.filter(shifts -> shifts.getStart().getDayOfMonth() == 5)
				.findFirst()
				.orElseThrow(IllegalStateException::new);
	}

	@Test
	public void testFullAssignment() {
		shifts.stream()
				.forEach(shift -> assignment = assignment.add(shift, employee));
		Optional<Shift> next = assignment.getNextUnassignedShift();
		assertFalse(next.isPresent());
	}

}