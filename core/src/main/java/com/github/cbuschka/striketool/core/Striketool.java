package com.github.cbuschka.striketool.core;

import com.github.cbuschka.striketool.core.io.ResourceResolver;
import com.github.cbuschka.striketool.core.io.RootResourceResolver;

import java.io.File;

public class Striketool {

    private RootResourceResolver resourceResolver = new RootResourceResolver();

    private Striketool() {
    }

    public static Striketool newInstance() {
        Striketool striketool = new Striketool();
        striketool.initResourceResolver();
        return striketool;
    }

    private void initResourceResolver() {
        File[] roots = File.listRoots();
        if (roots != null) {
            for (File root : roots) {
                resourceResolver.addFilesystemResolver(root);
            }
        }
    }

    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }
}
