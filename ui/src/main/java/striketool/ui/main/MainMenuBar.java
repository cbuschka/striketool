package striketool.ui.main;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionListener;

@Slf4j
public class MainMenuBar extends JMenuBar {
    private JMenuItem exitMenuItem;
    private JMenuItem openMenuItem;

    public MainMenuBar() {
        JMenu fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open...");
        fileMenu.add(openMenuItem);
        fileMenu.add(new JSeparator());
        exitMenuItem = new JMenuItem("Quit");
        fileMenu.add(exitMenuItem);
        add(fileMenu);
    }

    public void addExitClickedListener(ActionListener l) {
        this.exitMenuItem.addActionListener(l);
    }

    public void addFileOpenClickedListener(ActionListener l) {
        this.openMenuItem.addActionListener(l);
    }
}
