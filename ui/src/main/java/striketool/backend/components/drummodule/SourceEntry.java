package striketool.backend.components.drummodule;

import java.io.InputStream;

public interface SourceEntry {
    String getPath();

    InputStream getInputStream();
}
