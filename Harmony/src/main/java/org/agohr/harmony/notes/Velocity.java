package org.agohr.harmony.notes;

import lombok.Value;

/** speed of pressing down the note key */
@Value
public class Velocity {

	/**
	 * midi value.
	 */
	private final int value;

	public Velocity() {
		this(0x7F);
	}

	public Velocity(int value) {
		assert 0 <= value && value < 128;
		this.value = value;
	}

}
