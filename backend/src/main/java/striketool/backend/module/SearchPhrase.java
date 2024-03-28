package striketool.backend.module;

import lombok.SneakyThrows;

import java.io.File;
import java.util.List;

public class SearchPhrase {
    private List<String> words;

    public SearchPhrase(List<String> words) {
        this.words = words;
    }

    public boolean matches(File file) {
        return matches(file.getPath());
    }

    public boolean matches(String path) {
        for (String word : words) {
            if (!path.contains(word.toLowerCase())) {
                return false;
            }
        }

        return true;
    }
}
