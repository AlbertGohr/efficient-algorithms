package org.agohr.schiftschedule.vo;

public class EmployeeFactory {

	public static Employee getEmployee(long id, String name, Shifts candidates) {
		Preferences preferences = PreferencesFactory.getDefaultPreferences(candidates);
		return getEmployee(id, name, preferences);
	}

	public static Employee getEmployee(long id, String name, Preferences preferences) {
		return Employee.builder()
				.id(id)
				.name(name)
				.claimedNumberOfAssignments(preferences.size())
				.preferences(preferences)
				.build();
	}

}
