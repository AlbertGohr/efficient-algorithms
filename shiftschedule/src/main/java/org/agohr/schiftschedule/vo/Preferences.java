package org.agohr.schiftschedule.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

@Value
public class Preferences {

	private static final int avg = computeAvg();

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
		return sum == Preferences.avg * preferences.size();
	}

	public Rating preference(Shift shift) {
		assert preferences.containsKey(shift);
		return preferences.get(shift);
	}

	private static int computeAvg() {
		int dif = Rating.MaxRating.getRating() - Rating.MinRating.getRating();
		assert dif % 2 == 0;
		return Rating.MinRating.getRating() + dif / 2;
	}

}
