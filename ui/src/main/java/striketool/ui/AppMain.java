package striketool.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import striketool.ui.dialogs.session_window.AppController;
import striketool.ui.dialogs.session_window.AppModel;
import striketool.ui.dialogs.session_window.AppWindow;
import striketool.ui.dialogs.session_window.AppWorker;
import striketool.ui.module.DrumModuleService;
import striketool.ui.module.Simulator;

@Component
public class AppMain implements CommandLineRunner {
    @Autowired
    private DrumModuleService drumModuleService;
    @Autowired
    private Simulator simulator;
    @Autowired
    private AppWorker appWorker;

    @Override
    public void run(String... args) throws Exception {

        FlatLaf.setup(new FlatDarkLaf());

        AppModel appModel = new AppModel(drumModuleService, simulator, appWorker);
        AppController appControler = new AppController(appModel);
        AppWindow appWindow = new AppWindow(appControler, appModel);
        appWindow.open();

        AppWindow.waitForAllClosed();

        System.exit(0);
    }
}
