package striketool.ui.main;

import io.github.cbuschka.strike4j.instrument.Instrument;
import io.github.cbuschka.strike4j.instrument.InstrumentReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import striketool.ui.instrument_editor.InstrumentEditorModel;
import striketool.ui.instrument_editor.InstrumentEditorPanel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;

@Slf4j
public class MainWindow extends JFrame {
    private SessionModel sessionModel;
    private MainMenuBar menuBar;
    private JTabbedPane tabbedPane;
    private JSplitPane splitPane;

    public MainWindow(SessionModel sessionModel) {
        this.sessionModel = sessionModel;
        setSize(800, 600);
        setTitle("Striketool");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        buildMenu();
        // buildToolbar();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        buildSideBar();
        buildFooterBar();
        this.tabbedPane = new JTabbedPane();
        splitPane.setRightComponent(this.tabbedPane);
        splitPane.setDividerLocation(200);
        this.sessionModel.addListener(() -> SwingUtilities.invokeLater(MainWindow.this::updateView));
        updateView();
    }

    private void buildSideBar() {
        SideBar sideBar = new SideBar();
        splitPane.setLeftComponent(sideBar);
    }

    private void buildFooterBar() {
        StatusPanel panel = new StatusPanel();
        getContentPane().add(panel, BorderLayout.PAGE_END);
    }

    public void open() {
        setVisible(true);
    }

    private void buildMenu() {
        menuBar = new MainMenuBar(sessionModel);
        menuBar.addFileOpenClickedListener(this::fileOpenClicked);
        menuBar.addExitClickedListener(this::exitClicked);
        setJMenuBar(menuBar);
    }

    @SneakyThrows
    private void fileOpenClicked(ActionEvent actionEvent) {
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("/home/conni/work/fcd/github/cbuschka/strike4j/src/test/resources/"));
        fc.setDialogTitle("Open...");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileFilter sinFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || (f.isFile() && f.getName().toLowerCase().endsWith(".sin"));
            }

            @Override
            public String getDescription() {
                return "*.sin";
            }
        };
        fc.addChoosableFileFilter(sinFilter);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(sinFilter);
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            log.info("Opening: " + file.getName() + ".");

            try (InstrumentReader rd = new InstrumentReader(file.getName(), new FileInputStream(file));) {
                Instrument instrument = rd.read(false);
                InstrumentEditorPanel editor = new InstrumentEditorPanel(new InstrumentEditorModel(instrument));
                addEditor(file.getName(), editor);
            }
        } else {
            log.info("Open command cancelled by user.");
        }
    }

    public void addEditor(String title, InstrumentEditorPanel editor) {
        this.tabbedPane.add((String) null, editor);
        int index = this.tabbedPane.indexOfComponent(editor);
        TabComponent tabComponent = new TabComponent(title, this.tabbedPane);
        this.tabbedPane.setTabComponentAt(index, tabComponent);
    }

    private void buildToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton openButton = new JButton("Open...");
        toolBar.add(openButton);

        JButton rescanButton = new JButton("Rescan...");
        toolBar.add(rescanButton);

        getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    private void exitClicked(ActionEvent ev) {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void updateView() {
    }
}
