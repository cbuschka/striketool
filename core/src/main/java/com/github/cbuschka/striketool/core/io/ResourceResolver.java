package com.github.cbuschka.striketool.core.io;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface ResourceResolver {

    @SneakyThrows
    default File findOrFail(String name) {
        return findFirst(name).orElseThrow(() -> new FileNotFoundException(name));
    }

    default Optional<File> findFirst(String name) {
        List<File> resources = findAll(name);
        if (resources.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(resources.get(0));
    }

    List<File> findAll(String name);
}
