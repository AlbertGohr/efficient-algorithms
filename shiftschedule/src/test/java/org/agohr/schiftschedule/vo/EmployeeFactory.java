package org.agohr.schiftschedule.vo;

public class EmployeeFactory {

	public static Employee getEmployee(Shifts candidates) {
		Preferences preferences = PreferencesFactory.getDefaultPreferences(candidates);
		return getEmployee(preferences);
	}

	public static Employee getEmployee(Preferences preferences) {
		return Employee.builder()
				.id(1L)
				.name("agohr")
				.claimedNumberOfAssignments(preferences.size())
				.preferences(preferences)
				.build();
	}

}
