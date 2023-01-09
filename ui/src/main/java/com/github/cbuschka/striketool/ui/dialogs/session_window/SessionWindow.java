package com.github.cbuschka.striketool.ui.dialogs.session_window;

import com.github.cbuschka.striketool.ui.Session;
import com.github.cbuschka.striketool.ui.dialogs.sample_editor.SampleEditor;
import com.github.cbuschka.striketool.ui.dialogs.search.SearchPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SessionWindow {

    private static final Barrier BARRIER = new Barrier();

    private JFrame frame;
    private JTabbedPane tabbedPane;
    private Session session;

    private JLabel statusLabel;

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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setSize(screenSize.width * 3 / 4, screenSize.height * 3 / 4);
        this.frame.setLocation((screenSize.width - this.frame.getWidth()) / 2, (screenSize.height - this.frame.getHeight()) / 2);

        initMenuBar();

        Container contentArea = initContentArea();

        initStatusBar(contentArea, BorderLayout.SOUTH);

    }

    private void initStatusBar(Container contentArea, Object constraints) {
        StatusBar statusBar = new StatusBar(session);
        contentArea.add(statusBar, constraints);
    }

    private Container initContentArea() {
        Container contentPane = this.frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(0.4d);
        contentPane.add(splitPane, BorderLayout.CENTER);

        initSidePanel(splitPane, JSplitPane.LEFT);

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

    private void initSidePanel(Container contentPane, Object constraints) {
        SearchPanel searchPanel = new SearchPanel();
        contentPane.add(searchPanel, constraints);
    }

    public void open() {
        this.frame.setVisible(true);
        // addTab(new SampleEditor(session));
        BARRIER.add();
    }

    public static void waitForAllClosed() throws InterruptedException {
        BARRIER.waitFor();
    }

    public void addTab(SampleEditor editor) {
        this.tabbedPane.addTab("Sample #1", editor);
    }
}
