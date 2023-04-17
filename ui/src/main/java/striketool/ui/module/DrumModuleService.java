package striketool.ui.module;

import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrumModuleService {

    private DrumModuleAdapter activeAdapter;

    private List<DrumModuleAdapter> adapters = new ArrayList<>();

    private List<Listener> listeners = new ArrayList<>();

    public DrumModuleService(List<DrumModuleAdapter> adapters) {
        this.adapters = adapters;
    }

    public void addListener(Listener l) {
        this.listeners.add(l);
    }

    public Mode getMode() {
        if (this.activeAdapter == null) {
            return Mode.NONE;
        }

        return activeAdapter.getMode();
    }

    public void start() {

    }

    public void stop() {

    }

    public boolean isAvailable() {
        return activeAdapter != null && activeAdapter.isAvailable();
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public synchronized void scan() {
        if (activeAdapter == null) {
            for (DrumModuleAdapter adapter : adapters) {
                if (adapter.isAvailable()) {
                    activeAdapter = adapter;
                    fireModuleStatusChanged(true);
                }
            }
        } else {
            if (!activeAdapter.isAvailable()) {
                activeAdapter = null;
                fireModuleStatusChanged(false);
            }
        }
    }

    private void fireModuleStatusChanged(boolean available) {
        for (Listener listener : listeners) {
            listener.moduleStatusChanged(available);
        }
    }

    public String getName() {
        if (activeAdapter != null) {
            return activeAdapter.getName();
        }

        return "";
    }

    public interface Listener {
        void moduleStatusChanged(boolean available);
    }

    public List<String> search(SearchPhrase searchPhrase) {
        if (activeAdapter != null) {
            return activeAdapter.search(searchPhrase);
        }

        return Collections.emptyList();
    }
}
