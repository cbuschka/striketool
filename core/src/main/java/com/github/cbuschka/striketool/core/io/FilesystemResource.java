package com.github.cbuschka.striketool.core.io;

import lombok.AllArgsConstructor;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class FilesystemResource implements Resource {

    private FilesystemResolver resolver;
    private File file;

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public boolean isFile() {
        return file.isFile();
    }

    @Override
    public boolean isFolder() {
        return file.isDirectory();
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public boolean isDrumModuleResource() {
        return false;
    }

    @Override
    public String getDrumModulePath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public boolean isReadable() {
        return file.canRead();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return new FileOutputStream(file);
    }

    @Override
    public boolean isWritable() {
        return file.canWrite();
    }

    @Override
    public boolean isFilesystemResource() {
        return true;
    }

    @Override
    public File getFilesystemFile() {
        return file;
    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == null) {
            return false;
        }

        if (otherObj.getClass() != getClass()) {
            return false;
        }

        FilesystemResource other = (FilesystemResource) otherObj;
        return file.equals(other.file);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{file=" + file + "}";
    }

    @Override
    public Resource getParentResource() {
        return new FilesystemResource(resolver, file.getParentFile());
    }

    @Override
    public Optional<Resource> lookup(String path) {
        return resolver.lookup(this.file, path);
    }

    @Override
    public List<Resource> listResources() {
        File[] files = file.listFiles();
        List<Resource> resources = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                resources.add(new FilesystemResource(resolver, file));
            }
        }
        return resources;
    }

    @Override
    public Path getPath() {
        return file.toPath();
    }

    @Override
    public Resource create(String name) {
        if (isFile()) {
            throw new IllegalStateException("Cannot create file within file.");
        }

        return new FilesystemResource(this.resolver, new File(file, name));
    }
}
