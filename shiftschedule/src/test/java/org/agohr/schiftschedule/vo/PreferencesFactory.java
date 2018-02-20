package org.agohr.schiftschedule.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreferencesFactory {

	/**
	 * all preferences set to average.
	 */
	public static Preferences getDefaultPreferences(Shifts candidates) {
		Map<Shift, Rating> preferences = new HashMap<>();
		candidates.stream().forEach(shift -> preferences.put(shift, Rating.avg));
		return new Preferences(preferences);
	}

	public static Preferences getPreferences(List<Shift> candidates, int... ratings) {
		assert candidates.size() == ratings.length;
		Map<Shift, Rating> preferences = new HashMap<>();
		for (int i = 0; i < candidates.size(); ++i) {
			Shift candidate = candidates.get(i);
			Rating rating = new Rating(ratings[i]);
			preferences.put(candidate, rating);
		}
		return new Preferences(preferences);
	}

}
