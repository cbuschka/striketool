package striketool.ui.module;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class Simulator implements DrumModuleAdapter {

    private boolean available = false;

    private Mode mode = Mode.STANDARD;

    private File userCardRootDir;
    private File internalCardRootDir;

    public void start() {
        if (this.available) {
            return;
        }

        exportFiles();

        this.available = true;
    }

    @SneakyThrows
    private void exportFiles() {
        Path tempDir = Files.createTempDirectory("striketool");
        this.userCardRootDir = new File(tempDir.toFile(), "userCard");
        this.userCardRootDir.mkdirs();
        this.internalCardRootDir = new File(tempDir.toFile(), "internalCard");
        this.internalCardRootDir.mkdirs();
    }

    @SneakyThrows
    private void deleteFiles() {
        Files.delete(this.userCardRootDir.toPath());
        Files.delete(this.internalCardRootDir.toPath());
    }

    @Override
    public boolean isAvailable() {
        return available;
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
}
