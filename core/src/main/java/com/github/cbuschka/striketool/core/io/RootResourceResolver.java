package com.github.cbuschka.striketool.core.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RootResourceResolver extends CompositeResolver implements ResourceResolver {

    private DrumModuleResolver drumModuleResolver;

    private List<FilesystemResolver> filesystemResolvers = new ArrayList<>();

    protected List<ResourceResolver> getResolvers() {
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
