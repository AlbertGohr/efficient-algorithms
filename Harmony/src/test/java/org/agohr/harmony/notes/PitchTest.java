package org.agohr.harmony.notes;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PitchTest {

	@Test
	public void testPitchConstructor() {
		assertThat(new Pitch(OctavePitch.C, 0).getMidiValue(), is(60));
		assertThat(new Pitch(OctavePitch.C_SHARP, 0).getMidiValue(), is(61));
		assertThat(new Pitch(OctavePitch.C, 1).getMidiValue(), is(72));
	}

	@Test
	public void testHalftoneStep() {
		Pitch pitch = new Pitch(60);
		assertThat(pitch.halfToneStep(5).getMidiValue(), is(65));
	}

	@Test
	public void testGetOctavePitch() {
		Pitch pitch = new Pitch(OctavePitch.C, -1);
		assertThat(pitch.getOctavePitch(), is(OctavePitch.C));
	}

	@Test
	public void testGetOctave() {
		Pitch pitch = new Pitch(OctavePitch.G_SHARP, 2);
		assertThat(pitch.getOctave(), is(2));
	}

}
