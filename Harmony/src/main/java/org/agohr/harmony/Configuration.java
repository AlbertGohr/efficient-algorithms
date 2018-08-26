package org.agohr.harmony;

import lombok.Data;

@Data
public class Configuration {

	private String filename;

	private int seed;

	private int minPitch;
	private int maxPitch;
	private int firstPitch;

	private int minDuration;
	private int maxDuration;
	private int firstDuration;

}
