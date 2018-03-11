package org.agohr.harmony.notes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Note implements Rhythm {

    private final Pitch pitch;

    private final Fraction duration;

    private final Velocity velocity;

    /** Constructor. (default velocity)
     * @param pitch pitch
     * @param duration duration */
    public Note(final Pitch pitch, final Fraction duration) {
        this(pitch, duration, new Velocity());
    }

}