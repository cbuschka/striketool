package striketool.ui.module;

import java.io.File;
import java.util.List;

public interface DrumModuleAdapter {

    String getName();
    
    boolean isAvailable();

    Mode getMode();

    List<String> search(SearchPhrase searchPhrase);
}
