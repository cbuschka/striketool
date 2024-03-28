package striketool.backend.module;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public interface DrumModuleAdapter {

    String getName();

    boolean isAvailable();

    Mode getMode();

    List<String> search(SearchPhrase searchPhrase);

    Iterable<Entry> listSamples();

    void start();

    void stop();

     interface Entry {
        Type getType();

        String getName();

        boolean isInternal();

        int getVersionCount();
    }
}
