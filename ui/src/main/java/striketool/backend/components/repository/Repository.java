package striketool.backend.components.repository;

import io.github.cbuschka.strike4j.instrument.Instrument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Repository {
    private List<InstrumentEntry> instruments = new ArrayList<>();

    public void importInstrument(Instrument instrument) {
        findEntryByPath(instrument.getPath())
                .ifPresentOrElse((e) -> e.setInstrument(instrument),
                        () -> {
                            InstrumentEntry e = new InstrumentEntry(instrument);
                            instruments.add(e);
                        });
    }

    private Optional<InstrumentEntry> findEntryByPath(String path) {
        return instruments
                .stream().filter((e) -> e.instrument.getPath().equals(path))
                .findFirst();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class InstrumentEntry {
        private Instrument instrument;
    }
}
