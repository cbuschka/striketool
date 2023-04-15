package striketool.ui.main;

import striketool.backend.components.drummodule.DrumModule;

import java.util.ArrayList;
import java.util.List;

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
        if(isSimulatorEnabled()) {
            return;
        }
        appModel.getSimulator().ifPresent((s) -> drumModule = s);
        fireModelChanged();
    }

    public void disableSimulator() {
        if(isSimulatorEnabled()) {
            return;
        }
        drumModule = null;
        fireModelChanged();
    }

    public boolean isDrumModulePresent() {
        return drumModule != null;
    }

    public boolean isSimulatorPresent() {
        return appModel.getSimulator().isPresent();
    }

    public boolean isSimulatorEnabled() {
        return appModel.getSimulator().isPresent()
                && appModel.getSimulator().get() == drumModule;
    }
}
