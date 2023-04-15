package striketool.backend.components.drummodule;

import lombok.SneakyThrows;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

public class StrikeDrumModule implements DrumModule {

    @SneakyThrows
    public StrikeDrumModule() {

    }

    public boolean isPresent() {
        return Arrays.stream(MidiSystem.getMidiDeviceInfo())
                .peek((i) -> {
                    System.err.println(i.getVendor() + " " + i.getName() + " " + i.getDescription());
                })
                .anyMatch((i) -> i.getDescription().toLowerCase().contains("alesis strike"));
    }

    @Override
    public Stream<SourceEntry> listPresets() {
        return Collections.<SourceEntry>emptyList().stream();
    }
}
