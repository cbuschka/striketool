package com.github.cbuschka.striketool.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface Resource {

    boolean exists();

    default String getName() {
        Path path = getPath();
        return path.getName(path.getNameCount() - 1).toString();
    }

    Resource create(String name);

    boolean isFile();

    boolean isFolder();

    boolean isDrumModuleResource();

    String getDrumModulePath();

    InputStream openInputStream() throws IOException;

    boolean isReadable();

    OutputStream openOutputStream() throws IOException;

    boolean isWritable();

    boolean isFilesystemResource();

    File getFilesystemFile();

    Resource getParentResource();

    Optional<Resource> lookup(String subPath);

    List<Resource> listResources();

    Path getPath();
}
