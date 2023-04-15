package striketool.backend.usecases;

import io.github.cbuschka.strike4j.instrument.Instrument;
import io.github.cbuschka.strike4j.instrument.InstrumentReader;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import striketool.backend.components.repository.Repository;
import striketool.backend.components.drummodule.SourceEntry;

@Slf4j
@AllArgsConstructor
public class SourceScanner {
    private Repository repository;

    public void scan() {
    }

    private void importEntry(SourceEntry entry) {
        String path = entry.getPath();
        if (path.contains("Samples/")) {
            importSample(entry);
        } else if (path.contains("Instruments/") && path.endsWith(".sin")) {
            importInstrument(entry);
        } else if (path.contains("Kits/") && path.endsWith(".skt")) {
            importKit(entry);
        } else {
            log.warn("Unsupported entry: " + path);
        }


    }

    private void importKit(SourceEntry entry) {

    }

    @SneakyThrows
    private void importInstrument(SourceEntry entry) {
        log.info("Importing instrument {}...", entry.getPath());
        try (InstrumentReader rd = new InstrumentReader(entry.getPath(), entry.getInputStream());) {
            Instrument instrument = rd.read();
            repository.importInstrument(instrument);
        }
    }

    private void importSample(SourceEntry entry) {

    }
}
