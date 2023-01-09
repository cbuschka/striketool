package com.github.cbuschka.striketool.core.io;

import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface ResourceResolver {

    List<Resource> getRoots();

    @SneakyThrows
    default Resource findOrFail(String name) {
        return findFirst(name).orElseThrow(() -> new FileNotFoundException(name));
    }

    default Optional<Resource> findFirst(String name) {
        List<Resource> resources = findAll(name);
        if (resources.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(resources.get(0));
    }

    List<Resource> findAll(String name);

    default void close() {
    }

}
