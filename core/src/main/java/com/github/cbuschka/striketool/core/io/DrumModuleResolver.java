package com.github.cbuschka.striketool.core.io;

import java.util.ArrayList;
import java.util.List;

public class DrumModuleResolver implements ResourceResolver {

    private ResourceResolver internalResolver;
    private ResourceResolver userCardResolver;

    public DrumModuleResolver(ResourceResolver internalResolver, ResourceResolver userCardResolver) {
        this.internalResolver = internalResolver;
        this.userCardResolver = userCardResolver;
    }

    @Override
    public List<Resource> getRoots() {
        List<Resource> allResources = new ArrayList<>();
        if (userCardResolver != null) {
            allResources.addAll(userCardResolver.getRoots());
        }

        if (internalResolver != null) {
            allResources.addAll(internalResolver.getRoots());
        }

        return allResources;
    }

    @Override
    public List<Resource> findAll(String name) {
        List<Resource> allResources = new ArrayList<>();
        if (userCardResolver != null) {
            allResources.addAll(userCardResolver.findAll(name));
        }

        if (internalResolver != null) {
            allResources.addAll(internalResolver.findAll(name));
        }

        return allResources;
    }
}
