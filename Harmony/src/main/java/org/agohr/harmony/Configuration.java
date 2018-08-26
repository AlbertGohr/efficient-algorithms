package org.agohr.harmony;

import lombok.Data;

/**
 * compare sample application.yml
 */
@Data
public class Configuration {

	private String filename;

	private int seed;

	private int instrument;

	private int noteCount;

	private int minPitch;
	private int maxPitch;
	private int firstPitch;

	private int minDuration;
	private int maxDuration;
	private int firstDuration;

	private int poissonLambda;

	private double durationRho;

}
