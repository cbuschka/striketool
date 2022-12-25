package com.github.cbuschka.striketool.core.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FilesystemResolver implements ResourceResolver {

    private File baseDir;

    public FilesystemResolver(File baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public List<File> findAll(String name) {
        File file = new File(baseDir, name);
        if (file.exists() && file.isFile()) {
            return Collections.singletonList(file);
        }

        return Collections.emptyList();
    }
}
