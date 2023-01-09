package com.github.cbuschka.striketool.core.io;

import lombok.SneakyThrows;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileResourceResolver implements ResourceResolver {

    private String entryPrefix;

    private ZipFile zipFile;

    @SneakyThrows
    public ZipFileResourceResolver(String path, String entryPrefix) {
        this.zipFile = new ZipFile(path);
        this.entryPrefix = entryPrefix != null && !entryPrefix.isEmpty() && !entryPrefix.endsWith("/") ? entryPrefix + "/" : "";
    }


    @Override
    public List<Resource> getRoots() {
        return Collections.singletonList(new ZipFileResource(this, entryPrefix));
    }

    @Override
    public List<Resource> findAll(String name) {
        return zipFile.stream()
                .filter((entry) -> entry.getName().equals(name) || entry.getName().equals(name + "/"))
                .map(this::toResource)
                .collect(Collectors.toList());
    }

    List<Resource> list(String base) {
        return zipFile.stream()
                .filter((entry) -> entry.getName().startsWith(base) && entry.getName().substring(base.length()).split("/").length < 2)
                .map(this::toResource)
                .collect(Collectors.toList());
    }


    public Optional<Resource> lookup(String path, String subPath) {

        String newPath = concat(path, subPath);
        return zipFile.stream()
                .filter((entry) -> (entryPrefix + entry.getName()).equals(entryPrefix + newPath) || (entryPrefix + entry.getName()).equals(entryPrefix + newPath + "/"))
                .map(this::toResource)
                .map((r) -> (Resource) r)
                .findFirst();
    }

    public boolean exists(String path) {
        ZipEntry entry = zipFile.getEntry(entryPrefix + path);
        return entry != null;
    }


    public Resource create(String path, String name) {
        String newPath = concat(path, name);
        if (exists(newPath)) {
            throw new IllegalStateException("Already exists.");
        }

        return new ZipFileResource(this, newPath);

    }

    private String concat(String path, String name) {
        String fullPath = (path != null && !path.isEmpty() && !path.equals("/") ? path + "/" : path) + name;
        fullPath = fullPath.replaceAll("/+", "/");
        if (fullPath.startsWith("/")) {
            fullPath = fullPath.substring(1);
        }
        return fullPath;
    }

    public boolean isFolder(String path) {
        return exists(path) && path.endsWith("/");
    }

    private ZipFileResource toResource(ZipEntry entry) {
        String path = entry.getName();
        if (path.startsWith(entryPrefix)) {
            path = path.substring(entryPrefix.length());
        }
        return new ZipFileResource(this, path);
    }
}
