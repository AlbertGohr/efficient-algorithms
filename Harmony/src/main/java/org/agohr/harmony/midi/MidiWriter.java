package org.agohr.harmony.midi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.agohr.harmony.notes.Channel;
import org.agohr.harmony.notes.Fraction;
import org.agohr.harmony.notes.Note;
import org.agohr.harmony.notes.Pause;

/**
 * takes the internal data structure and writes a MIDI file.
 */
public class MidiWriter {

	/**
	 * writes the data into a midi file.
	 *
	 * @param name      the name of the file
	 * @param copyright the copyright of the file
	 * @param channels  the channels of the file
	 * @param filePath  the path of the file
	 */
	public final void write(final String name, final String copyright, final List<Channel> channels,
							final String filePath) {
		final List<Byte> bytes = this.createBytes(name, copyright, channels);
		try {
			final FileOutputStream fos = new FileOutputStream(filePath);

			fos.write(this.to_bytes(bytes));

			fos.close();
		} catch (final FileNotFoundException ex) {
			System.err.println("FileNotFoundException : " + ex);
		} catch (final IOException ioe) {
			System.err.println("IOException : " + ioe);
		}

	}

	/**
	 * creates the list of bytes.
	 *
	 * @param name      the name of the file
	 * @param copyright the copyright of the file
	 * @param channels  the channels of the file
	 * @return the bytes of the file
	 */
	private List<Byte> createBytes(final String name, final String copyright, final List<Channel> channels) {
		final List<Byte> bytes = new ArrayList<>();
		// 0-3: MIDI header: MThd
		bytes.add((byte) 0x4D);
		bytes.add((byte) 0x54);
		bytes.add((byte) 0x68);
		bytes.add((byte) 0x64);

		// 4-7: MIDI header length, always 6
		bytes.add((byte) 0x00);
		bytes.add((byte) 0x00);
		bytes.add((byte) 0x00);
		bytes.add((byte) 0x06);

		// 8-9: Single Multi-Channel Track
		bytes.add((byte) 0x00);
		bytes.add((byte) 0x00);

		// 10-11: Number of Tracks: 1
		bytes.add((byte) 0x00);
		bytes.add((byte) 0x01);

		// 12-13: Time-code based Time
		bytes.add((byte) 0x01);
		bytes.add((byte) 0xE0);

		// 14-17: Track Header: MTrk
		bytes.add((byte) 0x4D);
		bytes.add((byte) 0x54);
		bytes.add((byte) 0x72);
		bytes.add((byte) 0x6B);

		// 18-21: Track Header Length (set later)
		bytes.add((byte) 0x00);
		bytes.add((byte) 0x00);
		bytes.add((byte) 0x00);
		bytes.add((byte) 0x00);

		// Track Name
		final byte[] nameBytes = name.getBytes();
		assert nameBytes.length < 256;
		bytes.add((byte) 0x00);
		bytes.add((byte) 0xFF);
		bytes.add((byte) 0x03);
		bytes.add((byte) nameBytes.length);
		for (final byte nameByte : nameBytes) {
			bytes.add(nameByte);
		}

		// Copyright
		final byte[] copyrightBytes = copyright.getBytes();
		assert copyrightBytes.length < 256;
		bytes.add((byte) 0x00);
		bytes.add((byte) 0xFF);
		bytes.add((byte) 0x02);
		bytes.add((byte) copyrightBytes.length);
		for (final byte copyrightByte : copyrightBytes) {
			bytes.add(copyrightByte);
		}

		// time signature
		bytes.add((byte) 0x00);
		bytes.add((byte) 0xFF);
		bytes.add((byte) 0x58);
		bytes.add((byte) 0x04);
		bytes.add((byte) 0x04);
		bytes.add((byte) 0x02);
		bytes.add((byte) 0x18);
		bytes.add((byte) 0x08);

		// tempo
		bytes.add((byte) 0x00);
		bytes.add((byte) 0xFF);
		bytes.add((byte) 0x51);
		bytes.add((byte) 0x03);
		bytes.add((byte) 0x08);
		bytes.add((byte) 0x7A);
		bytes.add((byte) 0x23);

		for (final Channel channel : channels) {
			final int id = channel.getId();

			// instrument
			bytes.add((byte) 0x00);
			bytes.add((byte) (0xC0 + id));
			bytes.add((byte) channel.getInstrument().getMidiValue());

			// notes
			final List<Note> notes = channel.getNotes();
			for (final Note note : notes) {
				this.addNote(note, id, bytes);
			}

			// last note: pause (velocity=0)
			this.addNote(new Pause(new Fraction(1, 1)), id, bytes);
		}

		// End of Track
		bytes.add((byte) 0x00);
		bytes.add((byte) 0xFF);
		bytes.add((byte) 0x2F);
		bytes.add((byte) 0x00);

		//18-21: length
		final int l = bytes.size() - 22;
		final int n = 4;
		final byte[] length = this.toNBytes(l, n);
		for (int i = 0; i < n; ++i) {
			bytes.set(18 + i, length[i]);
		}

		return bytes;
	}

	/**
	 * converts the note to bytes and append them.
	 *
	 * @param note    the note
	 * @param bytes   the list of bytes
	 * @param channel the channel
	 */
	private void addNote(final Note note, final int channel, final List<Byte> bytes) {
		assert 0 <= channel && channel < 16;

		//delta time 0
		bytes.add((byte) 0x00);
		//note on
		bytes.add((byte) (0x90 + channel));
		//pitch
		bytes.add((byte) note.getPitch().getMidiValue());
		//velocity
		bytes.add((byte) note.getVelocity().getValue());

		//delta time: duration
		this.addDeltaTime(this.mapDuration(note.getDuration()), bytes);
		//note off
		bytes.add((byte) (0x80 + channel));
		//pitch
		bytes.add((byte) note.getPitch().getMidiValue());
		//velocity=0
		bytes.add((byte) 0x00);
	}

	/**
	 * adds the midi duration value to the bytes.
	 *
	 * @param midiDuration    midi duration value
	 * @param bytes the bytes
	 */
	private void addDeltaTime(final int midiDuration, final List<Byte> bytes) {
		assert midiDuration >= 0;
		int n = midiDuration;
		final List<Byte> delta = new ArrayList<>();
		final byte b = (byte) (n & 0b01111111);
		delta.add(b);
		while (n > 127) {
			n = n >> 7;
			final Byte b_i = (byte) (n | 0b10000000);
			delta.add(b_i);
		}
		for (int i = delta.size() - 1; i >= 0; --i) {
			bytes.add(delta.get(i));
		}
	}

	/**
	 * maps duration to midi value.
	 *
	 * @param duration duration
	 * @return midi value
	 */
	private int mapDuration(final Fraction duration) {
		final int whole = 1920; //duration of a whole note
		return (whole * duration.getUpper()) / duration.getLower();
	}

	/**
	 * converts x into n bytes (highest byte first).
	 *
	 * @param x_ value
	 * @param n  number of bytes
	 * @return the bytes
	 */
	private byte[] toNBytes(final int x_, final int n) {
		assert x_ >= 0;
		assert n >= 0;

		int x = x_;
		final byte[] bytes = new byte[n];
		for (int i = n - 1; i >= 0; --i) {
			bytes[i] = (byte) x;
			x = x >> 8;
		}
		return bytes;
	}

	/**
	 * convert List<Byte> to byte[].
	 *
	 * @param bytes the bytes
	 * @return the bytes
	 */
	private byte[] to_bytes(final List<Byte> bytes) {
		final byte[] bytes_ = new byte[bytes.size()];
		for (int i = 0; i < bytes.size(); ++i) {
			bytes_[i] = bytes.get(i);
		}
		return bytes_;
	}

}
