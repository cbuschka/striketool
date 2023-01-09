package com.github.cbuschka.striketool.core.io;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeResolver implements ResourceResolver {

    @Override
    public List<Resource> getRoots() {
        List<Resource> allRoots = new ArrayList<>();
        for (ResourceResolver resolver : getResolvers()) {
            List<Resource> resources = resolver.getRoots();
            allRoots.addAll(resources);
        }

        return allRoots;

    }

    @Override
    public List<Resource> findAll(String name) {
        List<Resource> allResources = new ArrayList<>();
        for (ResourceResolver resolver : getResolvers()) {
            List<Resource> resources = resolver.findAll(name);
            allResources.addAll(resources);
        }

        return allResources;
    }

    protected abstract List<ResourceResolver> getResolvers();
}
