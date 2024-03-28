package striketool.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import striketool.backend.module.DrumModuleService;
import striketool.backend.module.Simulator;
import striketool.backend.repository.Repository;
import striketool.ui.dialogs.app_window.AppController;
import striketool.ui.dialogs.app_window.AppModel;
import striketool.ui.dialogs.app_window.AppWindow;
import striketool.ui.dialogs.app_window.AppWorker;

@Component
public class AppMain implements CommandLineRunner {
    @Autowired
    private DrumModuleService drumModuleService;
    @Autowired
    private Simulator simulator;
    @Autowired
    private AppWorker appWorker;
    @Autowired
    private Repository repository;

    @Override
    public void run(String... args) throws Exception {

        FlatLaf.setup(new FlatDarkLaf());

        AppModel appModel = new AppModel(drumModuleService, simulator, appWorker, repository);
        AppController appControler = new AppController(appModel);
        AppWindow appWindow = new AppWindow(appControler, appModel);
        appWindow.open();

        AppWindow.waitForAllClosed();
    }
}
