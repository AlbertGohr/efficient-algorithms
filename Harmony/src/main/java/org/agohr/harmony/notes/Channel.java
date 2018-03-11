package org.agohr.harmony.notes;

import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

/** a music channel. */
@Value
public class Channel {

    private final int id;

    private final Instrument instrument;

    private final List<Note> notes;

    public String toString() {
        String notes = writeNotes();
        return "Channel(id=" + id + ", instrument=" + instrument + ", notes=\r\n" + notes + "\r\n)";
    }

    private String writeNotes() {
        return notes.stream()
                .map(note -> "\t" + note)
                .collect(Collectors.joining(",\r\n"));
    }

}