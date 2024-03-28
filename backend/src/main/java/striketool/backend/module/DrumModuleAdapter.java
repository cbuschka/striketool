package striketool.backend.module;

import java.util.List;

public interface DrumModuleAdapter {

    String getName();
    
    boolean isAvailable();

    Mode getMode();

    List<String> search(SearchPhrase searchPhrase);
}
