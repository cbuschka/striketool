package striketool.ui.main;

import lombok.extern.slf4j.Slf4j;
import striketool.backend.components.drummodule.DrumModule;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SessionModel {
    private List<Listener> listeners = new ArrayList<>();

    private AppModel appModel;

    private DrumModule drumModule;

    public interface Listener {
        void modelChanged();
    }

    public SessionModel(AppModel appModel) {
        this.appModel = appModel;
    }

    public void addListener(Listener l) {
        this.listeners.add(l);
    }

    private void fireModelChanged() {
        this.listeners.forEach(Listener::modelChanged);
    }

    public void enableSimulator() {
        if (!isSimulatorPresent()) {
            throw new IllegalStateException("Simulator not present.");
        }
        if (isSimulatorEnabled()) {
            return;
        }
        drumModule = appModel.getSimulator();
        fireModelChanged();
    }

    public void disableSimulator() {
        if (!isSimulatorPresent() || !isSimulatorEnabled()) {
            return;
        }
        drumModule = null;
        fireModelChanged();
    }


    public void enableReal() {
        if (!isRealPresent()) {
            throw new IllegalStateException("Real not present.");
        }
        if (isRealEnabled()) {
            return;
        }
        drumModule = appModel.getReal();
        fireModelChanged();
    }

    public void disableReal() {
        if (!isRealPresent() || !isRealEnabled()) {
            return;
        }
        drumModule = null;
        fireModelChanged();
    }

    public boolean isDrumModulePresent() {
        return drumModule != null;
    }

    public boolean isRealPresent() {
        return appModel.getReal().isPresent();
    }

    public boolean isRealEnabled() {
        return drumModule == appModel.getReal();
    }

    public boolean isSimulatorPresent() {
        return appModel.getSimulator().isPresent();
    }

    public boolean isSimulatorEnabled() {
        return appModel.getSimulator() == drumModule;
    }
}
