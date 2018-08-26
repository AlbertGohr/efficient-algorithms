package org.agohr.harmony;

import java.util.List;
import java.util.Random;

import org.agohr.harmony.composer.blackwalk.BlackWalk;
import org.agohr.harmony.composer.Composer;
import org.agohr.harmony.midi.MidiWriter;
import org.agohr.harmony.notes.Channel;

public final class Main {

    public static void main(final String[] args) {
        Random random = new Random(0);
        final Composer composer = new BlackWalk(random);
        final List<Channel> channels = composer.compose();

        System.out.println(channels);

        final MidiWriter mW = new MidiWriter();
        final String name = "demo";
        final String copyright = "Albert J. Gohr";
        final String strFilePath = "BlackWalk.mid";
        mW.write(name, copyright, channels, strFilePath);
    }
}
