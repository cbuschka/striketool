package striketool.backend.module;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Simulator implements DrumModuleAdapter {

    private Path tempDir;
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
        if (this.tempDir == null || !this.tempDir.toFile().isDirectory()) {
            this.tempDir = Files.createTempDirectory("striketool");
        }
        String internalCardZipFilename = System.getProperty("striketool.emulator.file", "./simulator.zip");
        this.internalCardZipFile = new ZipFile(internalCardZipFilename);
        this.userCardRootDir = new File(this.tempDir.toFile(), "userCard");
        this.userCardRootDir.mkdirs();
    }

    @SneakyThrows
    private void deleteFiles() {
        if (this.tempDir != null && this.tempDir.toFile().isDirectory()) {
            FileUtils.deleteTree(this.tempDir.toFile());
        }
        this.tempDir = null;
        this.userCardRootDir = null;
        if (this.internalCardZipFile != null) {
            this.internalCardZipFile.close();
        }
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

    @Override
    public Iterable<String> listSamples() {
        return internalCardZipFile
                .stream()
                .filter((e) -> e.getName().contains("/Samples/") && e.getName().endsWith(".wav"))
                .map(ZipEntry::getName)
                .collect(Collectors.toList());
    }
}
