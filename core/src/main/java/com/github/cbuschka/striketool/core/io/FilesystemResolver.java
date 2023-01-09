package com.github.cbuschka.striketool.core.io;

import java.io.File;
import java.nio.file.FileSystem;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

public class FilesystemResolver implements ResourceResolver {

    private File baseDir;

    public FilesystemResolver(File baseDir) {
        this.baseDir = baseDir;
    }

    @Override
    public List<Resource> getRoots() {
        File[] fileRoots = File.listRoots();
        if (fileRoots == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(fileRoots).map((file) -> new FilesystemResource(this, file)).collect(Collectors.toList());
    }

    @Override
    public List<Resource> findAll(String name) {
        Optional<Resource> resource = new FilesystemResource(this, baseDir).lookup(name);
        return resource.map(Collections::singletonList).orElse(Collections.emptyList());

    }

    public Optional<Resource> lookup(File file, String path) {
        File newFile = new File(file, path);
        if (!file.exists()) {
            return Optional.empty();
        }

        return Optional.of(new FilesystemResource(this, newFile));
    }

}
