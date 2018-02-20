package org.agohr.schiftschedule;

import org.agohr.schiftschedule.upperbounds.SimpleUpperBoundStrategy;
import org.agohr.schiftschedule.vo.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QualityEvaluatorTest {

	private final QualityEvaluator qualityEvaluator = new QualityEvaluator(new SimpleUpperBoundStrategy());

	private Assignment assignment;

	private Shift shift1;
	private Shift shift2;
	private Shift shift3;

	private Employee employee;

	@Before
	public void before() {
		shift1 = ShiftFactory.getEarlyShiftByDay(1);
		shift2 = ShiftFactory.getEarlyShiftByDay(2);
		shift3 = ShiftFactory.getEarlyShiftByDay(3);
		List<Shift> shiftList = Arrays.asList(shift1, shift2, shift3);
		Shifts shifts = new Shifts(shiftList);
		Preferences preferences = PreferencesFactory.getPreferences(shiftList, 2, 3, 4);
		employee = EmployeeFactory.getEmployee(1L, "agohr", preferences);
		assignment = new Assignment(shifts);
	}

	@Test
	public void testQualityWithoutShifts() {
		Assignment assignment = new Assignment();
		int quality = qualityEvaluator.computeQuality(assignment);
		assertEquals(0, quality);
	}

	@Test
	public void testQualityWithoutAssignedEmployees() {
		int quality = qualityEvaluator.computeQuality(assignment);
		int qualityBasedOnUpperBound = 3 * Rating.MaxRating.getRating();
		assertEquals(qualityBasedOnUpperBound, quality);
	}

	@Test
	public void testQualityWithSemiAssignment() {
		assignment = assignment.add(shift1, employee); // pref 2
		assignment = assignment.add(shift2, employee); // pref 3
		int actualQuality = qualityEvaluator.computeQuality(assignment);
		int expectedQuality = 2 + 3 + Rating.MaxRating.getRating(); // based on sum of preferences + upperBound
		assertEquals(expectedQuality, actualQuality);
	}

	@Test
	public void testQualityWithFullAssignment() {
		assignment = assignment.add(shift1, employee); // pref 2
		assignment = assignment.add(shift2, employee); // pref 3
		assignment = assignment.add(shift3, employee); // pref 4
		int actualQuality = qualityEvaluator.computeQuality(assignment);
		int expectedQuality = 2+3+4; // based on sum of preferences
		assertEquals(expectedQuality, actualQuality);
	}

}