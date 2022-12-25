package com.github.cbuschka.striketool.core.io;

import lombok.var;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RootResourceResolver implements ResourceResolver {

    private DrumModuleResolver drumModuleResolver;

    private List<FilesystemResolver> filesystemResolvers = new ArrayList<>();

    @Override
    public List<File> findAll(String name) {
        List<File> allResources = new ArrayList<>();
        for (ResourceResolver resolver : getResolvers()) {
            List<File> resources = resolver.findAll(name);
            allResources.addAll(resources);
        }

        return allResources;
    }

    private List<ResourceResolver> getResolvers() {
        List<ResourceResolver> resolvers = new ArrayList<>();
        if (drumModuleResolver != null) {
            resolvers.add(drumModuleResolver);
        }

        resolvers.addAll(filesystemResolvers);

        return resolvers;
    }

    public void addFilesystemResolver(File baseDir) {
        this.filesystemResolvers.add(new FilesystemResolver(baseDir));
    }
}
