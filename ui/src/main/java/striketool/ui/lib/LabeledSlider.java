package striketool.ui.lib;

import javax.swing.*;
import java.awt.*;

public class LabeledSlider extends JPanel {
    public LabeledSlider(String label, int min, int max) {
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
