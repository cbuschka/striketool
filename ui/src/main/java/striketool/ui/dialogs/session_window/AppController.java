package striketool.ui.dialogs.session_window;

public class AppController {
    private AppModel model;

    public AppController(AppModel model) {
        this.model = model;
    }

    public void startSimulator() {
        model.getAppWorker().runAsync(() -> {
            model.getSimulator().start();
            model.getDrumModuleService().scan();
        });

    }

    public void stopSimulator() {
        model.getAppWorker().runAsync(() -> {
            model.getSimulator().stop();
            model.getDrumModuleService().scan();
        });
    }
}
