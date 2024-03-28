package striketool.backend.module;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public interface DrumModuleAdapter {

    String getName();

    boolean isAvailable();

    Mode getMode();

    List<String> search(SearchPhrase searchPhrase);

    Iterable<String> listSamples();

    void start();

    void stop();
}
