package striketool.backend.components.drummodule;

import lombok.SneakyThrows;

import java.io.File;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

public class Simulator implements DrumModule {
    private ZipFile zipFile;

    @SneakyThrows
    public Simulator(File zipFile) {
        this.zipFile = new ZipFile(zipFile);
    }

    @Override
    public Stream<SourceEntry> listPresets() {
        return zipFile.stream()
                .filter((zipEntry) -> !zipEntry.getName().endsWith("/"))
                .map((zipEntry) -> (SourceEntry) new SourceZipEntry(zipFile, zipEntry));
    }
}
