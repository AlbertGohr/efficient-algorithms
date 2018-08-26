# Todos
* fix poisson distribution for lambda > 4 (or use a poisson library)
* provide diatonic and chromatic as alternatives for pentatonic.
* improve BlackWalk pitch boarder handling: do not change intermediate, only initial
* do not configure number of notes but number of bars.
* Write UnitTests for BlackWalk & MidiWriter

# Procedural Music Generation
This program composes music and writes a midi file.

# Build
mvn install

# Execution
Use the harmony-<Version>-jar-with-dependencies.jar
copy the application.yml into the same directory as the jar.
Via console: java -jar harmony-*.jar
Or use harmony.cmd