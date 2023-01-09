package com.github.cbuschka.striketool.core.alesis.drum_module;

import com.github.cbuschka.striketool.core.io.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class DrumModule {

    private DrumModuleAdapter adapter;

    private List<DrumModuleListener> listeners = new ArrayList<>();

    public DrumModule(DrumModuleAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean isAvailable() {
        return true;
    }

    public void addListener(DrumModuleListener listener) {
        this.listeners.add(listener);
    }

    public void start() {
        log.info("Started.");
    }

    public void stop() {
        log.info("Stopped.");
    }

    public boolean isInternalWriteEnabled() {
        return false;
    }

    public boolean isExternalWriteEnabled() {
        return false;
    }

    public List<Resource> findInstruments(SearchPhrase searchPhrase) {
        List<Resource> found = new ArrayList<>();

        Resource internalRoot = adapter.getInternalRoot();
        if (internalRoot != null) {
            find(internalRoot, searchPhrase, (r) -> r.getName().endsWith(".sin"), found);
        }

        Resource externalRoot = adapter.getExternalRoot();
        if (externalRoot != null) {
            find(externalRoot, searchPhrase, (r) -> r.getName().endsWith(".sin"), found);
        }

        return found;
    }

    private static void find(Resource baseDir, SearchPhrase searchPhrase, Function<Resource, Boolean> fileMatcher, List<Resource> found) {
        if (!baseDir.isFolder()) {
            return;
        }

        List<Resource> childResources = baseDir.listResources();
        for (Resource childResource : childResources) {
            if (fileMatcher.apply(childResource)
                    && searchPhrase.matches(childResource.getPath().toString())) {
                found.add(childResource);
            }

            if (childResource.isFolder()) {
                find(childResource, searchPhrase, fileMatcher, found);
            }
        }
    }
}
