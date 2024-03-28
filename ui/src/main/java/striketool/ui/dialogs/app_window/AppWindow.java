package striketool.ui.dialogs.app_window;

import lombok.SneakyThrows;
import striketool.backend.module.Mode;
import striketool.backend.usecases.sync.DiffEntry;
import striketool.backend.usecases.sync.SyncTask;
import striketool.ui.dialogs.sync_view.SyncStateModel;
import striketool.ui.dialogs.sync_view.SyncView;
import striketool.ui.swing.EDTUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class AppWindow {

    private static final Barrier BARRIER = new Barrier();

    private AppController controller;
    private AppModel model;

    private JFrame frame;
    private JTabbedPane tabbedPane;
    private StatusBar statusBar;
    private JMenuItem startSimulator;
    private JMenuItem stopSimulator;
    private ImagePane imagePane;

    public AppWindow(AppController controller, AppModel model) {
        this.model = model;
        this.controller = controller;

        initUI();
        this.model.addListener(this::updateFromModel);
        updateFromModel();
    }

    private void updateFromModel() {
        boolean drumModuleAvailable = model.getDrumModuleMode() != Mode.NONE;
        if (drumModuleAvailable) {
            statusBar.setMessage("Module (" + model.getDrumModuleName() + ") available.");
        } else {
            statusBar.setMessage("Module NOT available.");
        }
        startSimulator.setEnabled(!drumModuleAvailable && !model.getSimulator().isAvailable());
        stopSimulator.setEnabled(model.getSimulator().isAvailable());
        imagePane.setEnabled(drumModuleAvailable);
    }

    @SneakyThrows
    private void initUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.frame = new JFrame("Striketool");
        this.frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().setVisible(false);
                e.getWindow().dispose();
                BARRIER.free();
            }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setSize(screenSize.width * 3 / 4, screenSize.height * 3 / 4);
        this.frame.setLocation((screenSize.width - this.frame.getWidth()) / 2, (screenSize.height - this.frame.getHeight()) / 2);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setMinimumSize(new Dimension(400, 300));

        initMenuBar();

        Container contentArea = initContentArea();

        initStatusBar(contentArea, BorderLayout.SOUTH);
        initTabbedPanel(frame.getContentPane(), BorderLayout.CENTER);
        imagePane = new ImagePane("/module.png");
        /*
        frame.getContentPane().add(imagePane, BorderLayout.CENTER);

         */
    }

    private void initStatusBar(Container contentArea, Object constraints) {
        statusBar = new StatusBar();
        contentArea.add(statusBar, constraints);
    }

    private Container initContentArea() {
        Container contentPane = this.frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(0.4d);
        contentPane.add(splitPane, BorderLayout.CENTER);

        initTabbedPanel(splitPane, JSplitPane.RIGHT);
        return contentPane;
    }

    private void initMenuBar() {
        JMenuBar menuBar = buildMenuBar();
        this.frame.setJMenuBar(menuBar);
    }

    private void initTabbedPanel(Container contentPane, Object constraints) {
        tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane, constraints);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem fileSyncMenuItem = new JMenuItem("Sync");
        fileSyncMenuItem.addActionListener(actionEvent -> {
            openSync();
        });
        fileMenu.add(fileSyncMenuItem);

        JMenuItem fileExitMenuItem = new JMenuItem("Exit");
        fileExitMenuItem.addActionListener(actionEvent -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        fileMenu.add(fileExitMenuItem);

        JMenu toolsMenu = new JMenu("Tools");
        menuBar.add(toolsMenu);

        startSimulator = new JMenuItem("Start Simulator");
        startSimulator.addActionListener(actionEvent -> controller.startSimulator());
        toolsMenu.add(startSimulator);
        stopSimulator = new JMenuItem("Stop Simulator");
        stopSimulator.addActionListener(actionEvent -> controller.stopSimulator());
        toolsMenu.add(stopSimulator);

        return menuBar;
    }

    private void openSync() {
        SyncStateModel model = new SyncStateModel();
        SyncTask syncTask = new SyncTask(this.model.getSimulator(), this.model.getRepository(), new SyncTask.Handler() {
            @Override
            public void diffSeen(List<DiffEntry> diffEntries) {
                EDTUtils.runAsync(() -> model.add(diffEntries));
            }
        });
        this.model.getAppWorker().runAsync(syncTask);
        SyncView syncView = new SyncView(model);
        this.tabbedPane.add("Sync", syncView);
    }

    public void open() {
        this.frame.setVisible(true);
        BARRIER.add();
    }

    public static void waitForAllClosed() throws InterruptedException {
        BARRIER.waitFor();
    }
}