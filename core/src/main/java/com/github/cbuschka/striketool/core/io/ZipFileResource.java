package com.github.cbuschka.striketool.core.io;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ZipFileResource implements Resource {

    private ZipFileResourceResolver resolver;

    private String path;

    @Override
    public boolean exists() {
        return resolver.exists(path);
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public boolean isFolder() {
        return resolver.isFolder(path);
    }

    @Override
    public Path getPath() {
        String[] parts = path.split("/");
        String first = parts[0];
        String[] rest = new String[parts.length - 1];
        System.arraycopy(parts, 1, rest, 0, parts.length - 1);
        return Path.of(first, rest);
    }

    @Override
    public boolean isDrumModuleResource() {
        return false;
    }

    @Override
    public String getDrumModulePath() {
        return null;
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return null;
    }

    @Override
    public boolean isReadable() {
        return false;
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        throw new UnsupportedOperationException("Zip resource is read only.");
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Override
    public boolean isFilesystemResource() {
        return false;
    }

    @Override
    public File getFilesystemFile() {
        throw new UnsupportedOperationException("Not a file system resource.");
    }

    @Override
    public Resource getParentResource() {
        return null;
    }

    @Override
    public Resource create(String name) {
        return resolver.create(path, name);
    }

    @Override
    public Optional<Resource> lookup(String subPath) {
        return resolver.lookup(this.path, subPath);
    }

    @Override
    public List<Resource> listResources() {
        return resolver.list(this.path);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{path=" + path + "}";
    }


}
