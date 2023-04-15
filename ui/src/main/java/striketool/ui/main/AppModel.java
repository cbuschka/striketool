package striketool.ui.main;

import striketool.backend.components.drummodule.Simulator;

import java.io.File;
import java.util.Optional;

public class AppModel {
    private final File backupZipFilePath = new File("strike-sdcard-bak.zip");
    private Simulator simulator;

    public AppModel() {
        if( backupZipFilePath.isFile()) {
            this.simulator = new Simulator(backupZipFilePath);
        }
    }

    public Optional<Simulator> getSimulator() {
        return Optional.ofNullable(simulator);
    }

}
