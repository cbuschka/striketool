package striketool.backend.components.drummodule;

import java.util.stream.Stream;

public interface DrumModule {
    Stream<SourceEntry> listPresets();
}
