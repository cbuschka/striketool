package striketool.ui.main;

import io.github.cbuschka.strike4j.instrument.Instrument;
import io.github.cbuschka.strike4j.instrument.InstrumentReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import striketool.ui.util.Barrier;
import striketool.ui.util.UIUtils;
import striketool.ui.instrument_editor.InstrumentEditorModel;
import striketool.ui.instrument_editor.InstrumentEditorPanel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;

@Slf4j
public class MainController {
    private MainWindow mainWindow;
    private Barrier barrier = new Barrier();


    public MainController() {
    }

    public synchronized void open() {
        barrier.add();
        this.mainWindow = new MainWindow(new SessionModel(new AppModel()));
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                log.info("Main window closed.");
                barrier.free();
            }
        });
        mainWindow.open();

        this.mainWindow.open();
    }

    public void waitFor() throws InterruptedException {
        barrier.waitFor();
    }

    @SneakyThrows
    public void openInstrumentFile(File file) {
        try (InstrumentReader rd = new InstrumentReader(file.getName(), new FileInputStream(file));) {
            Instrument instrument = rd.read(false);
            UIUtils.runAsyncOnUIThread(() -> {
                InstrumentEditorPanel editor = new InstrumentEditorPanel(new InstrumentEditorModel(instrument));
                mainWindow.addEditor(file.getName(), editor);
            });
        }

    }
}
