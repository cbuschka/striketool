package striketool.ui.dialogs.app_window;

import striketool.backend.module.DrumModuleService;
import striketool.backend.module.Mode;
import striketool.backend.module.Simulator;
import striketool.backend.repository.Repository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AppModel {

    private final Repository repository;
    private AppWorker appWorker;
    private List<Listener> listeners = new ArrayList<>();

    private DrumModuleService drumModuleService;

    private Simulator simulator;

    public AppModel(DrumModuleService drumModuleService, Simulator simulator, AppWorker appWorker, Repository repository) {
        this.appWorker = appWorker;
        this.simulator = simulator;
        this.drumModuleService = drumModuleService;
        this.drumModuleService.addListener(available -> fireModelChanged());
        this.repository = repository;
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public void startSimulator() {
        this.simulator.start();
        fireModelChanged();
    }

    public void stopSimulator() {
        this.simulator.stop();
        fireModelChanged();
    }

    public DrumModuleService getDrumModuleService() {
        return drumModuleService;
    }

    public Repository getRepository() {
        return repository;
    }

    public interface Listener {
        void modelChanged();
    }

    public void addListener(Listener l) {
        this.listeners.add(l);
    }

    public String getDrumModuleName() {
        return drumModuleService.getName();
    }

    public Mode getDrumModuleMode() {
        return drumModuleService.getMode();
    }

    private void fireModelChanged() {
        SwingUtilities.invokeLater(() -> listeners.forEach(Listener::modelChanged));
    }

    public AppWorker getAppWorker() {
        return appWorker;
    }
}
