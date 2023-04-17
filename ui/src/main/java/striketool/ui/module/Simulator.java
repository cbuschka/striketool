package striketool.ui.module;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Simulator implements DrumModuleAdapter {

    private boolean available = false;

    private Mode mode = Mode.STANDARD;

    private ZipFile internalCardZipFile;
    private File userCardRootDir;

    public void start() {
        if (this.available) {
            return;
        }

        exportFiles();

        this.available = true;
    }

    @SneakyThrows
    private void exportFiles() {
        String internalCardZipFilename = System.getProperty("striketool.ui.module.Simulator.zipFile", "./simulator.zip");
        this.internalCardZipFile = new ZipFile(internalCardZipFilename);
        Path tempDir = Files.createTempDirectory("striketool");
        this.userCardRootDir = new File(tempDir.toFile(), "userCard");
        this.userCardRootDir.mkdirs();
    }

    @SneakyThrows
    private void deleteFiles() {
        Files.delete(this.userCardRootDir.toPath());
        this.userCardRootDir = null;
        this.internalCardZipFile.close();
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getName() {
        return "Simulator";
    }

    public void stop() {
        if (!this.available) {
            return;
        }

        deleteFiles();

        this.available = false;
    }

    public Mode getMode() {
        return mode;
    }

    @Override
    public List<String> search(SearchPhrase searchPhrase) {
        List<String> entries = internalCardZipFile
                .stream()
                .filter((e) -> searchPhrase.matches(e.getName()))
                .map(ZipEntry::getName)
                .collect(Collectors.toList());

        // search user card

        return entries;
    }
}
