package striketool.ui.dialogs.app_window;

import striketool.ui.module.DrumModuleService;
import striketool.ui.module.Mode;
import striketool.ui.module.Simulator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AppModel {

    private AppWorker appWorker;
    private List<Listener> listeners = new ArrayList<>();

    private DrumModuleService drumModuleService;

    private Simulator simulator;

    public AppModel(DrumModuleService drumModuleService, Simulator simulator, AppWorker appWorker) {
        this.appWorker = appWorker;
        this.simulator = simulator;
        this.drumModuleService = drumModuleService;
        this.drumModuleService.addListener(available -> fireModelChanged());
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
