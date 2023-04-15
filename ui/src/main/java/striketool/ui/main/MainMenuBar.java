package striketool.ui.main;

import lombok.extern.slf4j.Slf4j;
import striketool.ui.util.UIUtils;

import javax.swing.*;
import java.awt.event.ActionListener;

@Slf4j
public class MainMenuBar extends JMenuBar {
    private final JCheckBoxMenuItem realMenuItem;
    private JCheckBoxMenuItem simulatorMenuItem;
    private JMenuItem rescanMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem openMenuItem;
    private SessionModel sessionModel;

    public MainMenuBar(SessionModel sessionModel) {
        this.sessionModel = sessionModel;
        JMenu fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open...");
        fileMenu.add(openMenuItem);
        fileMenu.add(new JSeparator());
        exitMenuItem = new JMenuItem("Quit");
        fileMenu.add(exitMenuItem);
        add(fileMenu);

        JMenu moduleMenu = new JMenu("Module");
        moduleMenu.add(new JSeparator());
        realMenuItem = new JCheckBoxMenuItem("Real");
        moduleMenu.add(realMenuItem);
        realMenuItem.addActionListener(e -> {
            UIUtils.runAsyncInBackground(() -> {
                if (sessionModel.isRealPresent() && !sessionModel.isRealEnabled()) {
                    sessionModel.enableReal();
                } else {
                    sessionModel.disableReal();
                }
            });
        });
        simulatorMenuItem = new JCheckBoxMenuItem("Simulator");
        moduleMenu.add(simulatorMenuItem);
        simulatorMenuItem.addActionListener(e -> {
            UIUtils.runAsyncInBackground(() -> {
                if (sessionModel.isSimulatorPresent() && !sessionModel.isSimulatorEnabled()) {
                    sessionModel.enableSimulator();
                } else {
                    sessionModel.disableSimulator();
                }
            });
        });
        rescanMenuItem = new JMenuItem("Rescan...");
        rescanMenuItem.addActionListener((ev) -> {
            // start rescan
        });
        moduleMenu.add(rescanMenuItem);
        add(moduleMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About...");
        helpMenu.add(aboutMenuItem);
        add(helpMenu);

        sessionModel.addListener(() -> SwingUtilities.invokeLater(this::updateView));
        updateView();
    }


    public void addExitClickedListener(ActionListener l) {
        this.exitMenuItem.addActionListener(l);
    }

    public void addFileOpenClickedListener(ActionListener l) {
        this.openMenuItem.addActionListener(l);
    }

    void updateView() {
        rescanMenuItem.setEnabled(sessionModel.isDrumModulePresent());
        simulatorMenuItem.setEnabled(sessionModel.isSimulatorPresent());
        simulatorMenuItem.setSelected(sessionModel.isSimulatorEnabled());
        realMenuItem.setEnabled(sessionModel.isRealPresent());
        realMenuItem.setSelected(sessionModel.isRealEnabled());
    }
}
