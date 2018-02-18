package org.agohr.schiftschedule.vo;

import java.util.HashMap;
import java.util.Map;

public class PreferencesFactory {

	/**
	 * all preferences set to average.
	 */
	public static Preferences getDefaultPreferences(Shifts candidates) {
		Map<Shift, Rating> preferences = new HashMap<>();
		candidates.stream()
				.forEach(shift -> preferences.put(shift, Rating.avg));
		return new Preferences(preferences);
	}

}
