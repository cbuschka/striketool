package striketool.ui.instrument_editor;

import io.github.cbuschka.strike4j.instrument.Instrument;

import java.util.ArrayList;
import java.util.List;

public class InstrumentEditorModel {
    private List<Listener> listeners = new ArrayList<>();

    private Instrument instrument;

    public interface Listener {
        void modelChanged();
    }

    public InstrumentEditorModel(Instrument instrument) {
        this.instrument = instrument;
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void setLevel(int level) {
        this.instrument.setLevel(level);
        fireModelChanged();
    }

    public int getLevel() {
        return instrument.getLevel();
    }

    private void fireModelChanged() {
        this.listeners.forEach(Listener::modelChanged);
    }
}
