package org.agohr.harmony.scale;

import org.agohr.harmony.notes.OctavePitch;

public class ScaleFactory {

	public Scale byName(String name) {
		String cleanedName = name.trim().toUpperCase();
		if (cleanedName.contains("MAJOR")) {
			return major(cleanedName);
		}
		switch (cleanedName) {
			case "PENTATONIC":
				return new Pentatonic();
			case "CHROMATIC":
				return new Chromatic();
			default:
				throw new IllegalArgumentException("Unknown scale: " + cleanedName);
		}
	}

	private Scale major(String name) {
		String[] split = name.split("-");
		String tonic = split[0];
		OctavePitch octavePitch = OctavePitch.byName(tonic);
		return new Major(octavePitch);
	}

}
