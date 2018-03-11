package org.agohr.harmony.notes;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OctavePitchTest {

	@Test
	public void testIsBlack() {
		assertTrue(OctavePitch.Cis.isBlack());
		assertTrue(OctavePitch.Dis.isBlack());
		assertTrue(OctavePitch.Fis.isBlack());
		assertTrue(OctavePitch.Gis.isBlack());
		assertTrue(OctavePitch.Ais.isBlack());
	}

	@Test
	public void testIsNotBlack() {
		assertFalse(OctavePitch.C.isBlack());
		assertFalse(OctavePitch.D.isBlack());
		assertFalse(OctavePitch.E.isBlack());
		assertFalse(OctavePitch.F.isBlack());
		assertFalse(OctavePitch.G.isBlack());
		assertFalse(OctavePitch.A.isBlack());
		assertFalse(OctavePitch.B.isBlack());
	}

}
