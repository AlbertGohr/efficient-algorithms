package org.agohr.harmony.scale;

public class ScaleFactory {

	public Scale byName(String name) {
		String cleanedName = name.trim().toUpperCase();
		switch (cleanedName) {
			case "C-MAJOR":
				return new CMajor();
			case "PENTATONIC":
				return new Pentatonic();
			case "CHROMATIC":
				return new Chromatic();
			default:
				throw new IllegalArgumentException("Unknown scale: " + cleanedName);
		}
	}

}
