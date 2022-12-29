package com.github.cbuschka.striketool.ui.dialogs.session_window;

import com.github.cbuschka.striketool.ui.Session;
import com.github.cbuschka.striketool.ui.dialogs.sample_editor.SampleEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SessionWindow {

    private static final Barrier BARRIER = new Barrier();

    private JFrame frame;
    private JTabbedPane tabbedPane;
    private Session session;

    public SessionWindow(Session session) {
        this.session = session;

        initUI();
    }

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
        JMenuBar menuBar = buildMenuBar();
        this.frame.setJMenuBar(menuBar);
        this.frame.setSize(800, 600);
        tabbedPane = new JTabbedPane();
        this.frame.getContentPane().add(tabbedPane);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem fileNewSampleMenuItem = new JMenuItem("New Sample");
        fileNewSampleMenuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTab(new SampleEditor(session));
            }
        });
        fileMenu.add(fileNewSampleMenuItem);
        return menuBar;
    }

    public void open() {
        this.frame.setVisible(true);
        addTab(new SampleEditor(session));
        BARRIER.add();
    }

    public static void waitForAllClosed() throws InterruptedException {
        BARRIER.waitFor();
    }

    public void addTab(SampleEditor editor) {
        this.tabbedPane.addTab("Sample #1", editor);
    }
}
