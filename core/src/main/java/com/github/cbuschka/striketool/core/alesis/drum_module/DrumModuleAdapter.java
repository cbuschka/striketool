package com.github.cbuschka.striketool.core.alesis.drum_module;

import com.github.cbuschka.striketool.core.io.DrumModuleResolver;
import com.github.cbuschka.striketool.core.io.Resource;

import java.util.concurrent.CompletableFuture;

public interface DrumModuleAdapter {

    void start();

    void stop();

    Resource getInternalRoot();

    Resource getExternalRoot();
}
