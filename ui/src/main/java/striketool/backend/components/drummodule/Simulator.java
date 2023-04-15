package striketool.backend.components.drummodule;

import lombok.SneakyThrows;

import java.io.File;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

public class Simulator implements DrumModule {
    private File file;
    private ZipFile zipFile;

    @SneakyThrows
    public Simulator(File file) {
        this.file = file;
    }

    @Override
    public boolean isPresent() {
        return this.file.isFile();
    }

    @SneakyThrows
    @Override
    public Stream<SourceEntry> listPresets() {
        if (zipFile == null) {
            zipFile = new ZipFile(file);
        }

        return zipFile.stream()
                .filter((zipEntry) -> !zipEntry.getName().endsWith("/"))
                .map((zipEntry) -> (SourceEntry) new SourceZipEntry(zipFile, zipEntry));
    }
}
