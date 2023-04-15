package striketool.ui.instrument_editor;

import javax.swing.*;
import java.awt.*;

public class InstrumentEditorPanel extends JPanel {
    private InstrumentEditorModel model;

    public InstrumentEditorPanel(InstrumentEditorModel model) {
        this.model = model;
        setLayout(new FlowLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        mainPanel.add(new Knob("Level", 0, 99));
        mainPanel.add(new Knob("Pan", 0, 99));
        mainPanel.add(new Knob("Decay", 0, 99));
        add(mainPanel);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.LINE_AXIS));
        filterPanel.add(new JRadioButton("Lopass"));
        filterPanel.add(new JRadioButton("Hipass"));
        filterPanel.add(new Knob("Cutoff", 0, 99));
        add(filterPanel);

        JPanel tunePanel = new JPanel();
        tunePanel.setLayout(new BoxLayout(tunePanel, BoxLayout.LINE_AXIS));
        tunePanel.add(new Knob("Semitone", 0, 99));
        tunePanel.add(new Knob("Fine", 0, 99));
        add(tunePanel);


    }
}
