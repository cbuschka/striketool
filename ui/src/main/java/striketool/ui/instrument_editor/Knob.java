package striketool.ui.instrument_editor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Knob extends JPanel {
    public Knob(String label, int min, int max) {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), label, 4, 0, null, null));

        setLayout(new BorderLayout());
        JSlider slider = new JSlider(min, max, 0);
        slider.setMinorTickSpacing(1);
        slider.setMinorTickSpacing(10);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        add(slider, BorderLayout.CENTER);
    }
}
