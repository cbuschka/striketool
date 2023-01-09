package com.github.cbuschka.striketool.core.alesis.drum_module.simulator;

import com.github.cbuschka.striketool.core.alesis.drum_module.DrumModuleAdapter;
import com.github.cbuschka.striketool.core.io.Resource;
import com.github.cbuschka.striketool.core.io.ZipFileResource;
import com.github.cbuschka.striketool.core.io.ZipFileResourceResolver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Simulator implements DrumModuleAdapter {

    private ZipFileResourceResolver resolver;

    @SneakyThrows
    @Override
    public void start() {
        String simulatorZip = "/home/conni/work/fcd/github/cbuschka/striketool/simulator.zip";

        this.resolver = new ZipFileResourceResolver(simulatorZip, "bak");
    }

    @SneakyThrows
    @Override
    public void stop() {
        this.resolver.close();
    }

    @Override
    public Resource getExternalRoot() {
        return null;
    }

    @Override
    public Resource getInternalRoot() {
        return new ZipFileResource(resolver, "");
    }

}
