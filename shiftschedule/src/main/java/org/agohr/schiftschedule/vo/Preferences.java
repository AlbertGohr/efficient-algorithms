package org.agohr.schiftschedule.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
public class Preferences {

	@Getter(AccessLevel.NONE)
	private final Map<Shift, Rating> preferences;

	public Preferences(Map<Shift, Rating> preferences) {
		this.preferences = new HashMap<>(preferences);
		assert avg();
	}

	private boolean avg() {
		if (preferences.isEmpty()) {
			return true;
		}
		int sum = preferences.values().stream()
				.map(Rating::getRating)
				.mapToInt(Integer::intValue)
				.sum();
		return sum == Rating.avg.getRating() * preferences.size();
	}

	public Rating preference(Shift shift) {
		assert preferences.containsKey(shift);
		return preferences.get(shift);
	}

}
