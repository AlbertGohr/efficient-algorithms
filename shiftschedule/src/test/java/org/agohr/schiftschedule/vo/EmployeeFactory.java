package org.agohr.schiftschedule.vo;

public class EmployeeFactory {

	public static Employee getEmployee(Shifts candidates) {
		Preferences preferences = PreferencesFactory.getDefaultPreferences(candidates);
		return Employee.builder()
				.id(1L)
				.name("agohr")
				.claimedNumberOfAssignments(candidates.size())
				.candidates(candidates)
				.preferences(preferences)
				.build();
	}

}
