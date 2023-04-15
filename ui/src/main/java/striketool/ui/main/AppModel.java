package striketool.ui.main;

import striketool.backend.components.drummodule.Simulator;
import striketool.backend.components.drummodule.StrikeDrumModule;

import java.io.File;
import java.util.Optional;

public class AppModel {
    private final File backupZipFilePath = new File("strike-sdcard-bak.zip");
    private Simulator simulator = new Simulator(backupZipFilePath);
    private StrikeDrumModule real = new StrikeDrumModule();

    public AppModel() {
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public StrikeDrumModule getReal() {
        return real;
    }
}
