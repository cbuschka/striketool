package striketool.ui.main;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

@Slf4j
public class MainMenuBar extends JMenuBar {
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
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
        add(fileMenu);

        JMenu moduleMenu = new JMenu("Module");
        moduleMenu.add(new JSeparator());
        simulatorMenuItem = new JCheckBoxMenuItem("Simulator");
        moduleMenu.add(simulatorMenuItem);
        simulatorMenuItem.addChangeListener(e -> {
            boolean enabled = simulatorMenuItem.getState();
            if (enabled) {
                sessionModel.enableSimulator();
            } else {
                sessionModel.disableSimulator();
            }
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
        simulatorMenuItem.setState(sessionModel.isSimulatorEnabled());
    }
}
