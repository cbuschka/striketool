package com.github.cbuschka.striketool.core.alesis.drum_module.simulator;

import com.github.cbuschka.striketool.core.io.Resource;
import com.github.cbuschka.striketool.core.io.ZipFileResource;
import com.github.cbuschka.striketool.core.io.ZipFileResourceResolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class ZipResolverTest {

    @TempDir
    private Path tempDir;

    ZipFileResourceResolver zipFileResourceResolver;

    @BeforeEach
    public void setUp() throws Exception {
        File file = givenSimulatorZip();

        zipFileResourceResolver = new ZipFileResourceResolver(file.toPath().toString(), "prefix");

        System.err.println(file);
    }

    private File givenSimulatorZip() throws IOException {
        File file = new File(tempDir.toFile(), "simulator.zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
        zipOutputStream.putNextEntry(new ZipEntry("prefix/Instruments/"));
        zipOutputStream.closeEntry();
        zipOutputStream.putNextEntry(new ZipEntry("prefix/Kits/"));
        zipOutputStream.closeEntry();
        zipOutputStream.close();
        return file;
    }

    @AfterEach
    public void shutdown() {
        zipFileResourceResolver.close();
    }

    @Test
    void test() {

        List<Resource> roots = zipFileResourceResolver.getRoots();
        assertThat(roots).hasSize(1);
        Resource root = roots.get(0);

        List<Resource> resources = root.listResources();
        assertThat(resources).isNotEmpty();

        Optional<Resource> optInstruments = root.lookup("Instruments");
        assertThat(optInstruments).isPresent();
        Resource instruments = optInstruments.get();
        assertThat(instruments.exists()).isTrue();
        assertThat(instruments.getName()).isEqualTo("Instruments");
        assertThat(instruments.isFolder()).isTrue();
        System.err.println(resources);
    }

}