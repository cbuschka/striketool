package striketool.ui.instrument_editor;

import striketool.ui.lib.LabeledSlider;
import striketool.ui.util.GBC;
import striketool.ui.lib.LabelPanel;
import striketool.ui.util.UIUtils;

import javax.swing.*;
import java.awt.*;

public class InstrumentEditorPanel extends JPanel {
    private final InstrumentEditorModel model;

    public InstrumentEditorPanel(InstrumentEditorModel model) {
        this.model = model;
        setLayout(new GridBagLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Sample"));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(new LabeledSlider("Level", 0, 99));
        mainPanel.add(new LabeledSlider("Pan", -50, 50));
        mainPanel.add(new LabeledSlider("Decay", 0, 99));
        mainPanel.add(new LabelPanel<>("Loop mode", new JComboBox<>(new String[]{"Single play", "Loop on"})));
        add(mainPanel, GBC.at(0, 0).gridh(2).build());

        JPanel filterPanel = new JPanel();
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.PAGE_AXIS));
        filterPanel.add(new LabelPanel<>("Type", new JComboBox<>(new String[]{"Lopass", "Hipasss"})));
        filterPanel.add(new LabeledSlider("Cutoff", 0, 99));
        add(filterPanel, GBC.at(1, 0).build());

        JPanel tunePanel = new JPanel();
        tunePanel.setBorder(BorderFactory.createTitledBorder("Tuning"));
        tunePanel.setLayout(new BoxLayout(tunePanel, BoxLayout.PAGE_AXIS));
        tunePanel.add(new LabeledSlider("Semitone", 0, 99));
        tunePanel.add(new LabeledSlider("Fine", 0, 99));
        add(tunePanel, GBC.at(1, 1).build());

        JPanel mappingPanel = new JPanel();
        mappingPanel.setBorder(BorderFactory.createTitledBorder("Range Mapping"));
        mappingPanel.setLayout(new FlowLayout());
        mappingPanel.add(new JList<>());
        JPanel mappingDetailsPanel = new JPanel();
        mappingDetailsPanel.setLayout(new FlowLayout());
        mappingDetailsPanel.setBorder(BorderFactory.createTitledBorder("Samples"));
        mappingDetailsPanel.add(new JList<>());
        mappingPanel.add(mappingDetailsPanel);
        add(mappingPanel, GBC.at(0, 2).size(2, 1).weighty(10.0d).build());

        model.addListener(() -> UIUtils.runAsyncOnUIThread(InstrumentEditorPanel.this::updateView));
    }

    private void updateView() {
    }
}
