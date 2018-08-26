package org.agohr.harmony.scale;

import org.agohr.harmony.notes.Pitch;

public interface Scale {
	boolean isInScale(Pitch pitch);

	Pitch up(Pitch pitch);

	Pitch down(Pitch pitch);
}
