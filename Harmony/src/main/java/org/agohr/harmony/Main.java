package org.agohr.harmony;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.agohr.harmony.composer.blackwalk.BlackWalk;
import org.agohr.harmony.composer.Composer;
import org.agohr.harmony.midi.MidiWriter;
import org.agohr.harmony.notes.Channel;
import org.yaml.snakeyaml.Yaml;

public final class Main {

    public static void main(final String[] args) throws IOException {

		Configuration configuration = loadConfiguration();

		Random random = new Random(configuration.getSeed());
        final Composer composer = new BlackWalk(random);
        final List<Channel> channels = composer.compose();

        System.out.println(channels);

        final MidiWriter mW = new MidiWriter();
        final String name = "demo";
        final String copyright = "harmony";
        mW.write(name, copyright, channels, configuration.getFilename());
    }

	private static Configuration loadConfiguration() throws IOException {
		Yaml yaml = new Yaml();
		Path path = Paths.get("application.yml");
		try(InputStream in = Files.newInputStream(path) ) {
			return yaml.loadAs( in, Configuration.class );
		}
	}
}
