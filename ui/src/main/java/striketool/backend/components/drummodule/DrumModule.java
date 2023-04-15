package striketool.backend.components.drummodule;

import java.util.stream.Stream;

public interface DrumModule {
    boolean isPresent();
    Stream<SourceEntry> listPresets();
}
