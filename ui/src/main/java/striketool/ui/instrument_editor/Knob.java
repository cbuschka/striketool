package striketool.ui.instrument_editor;

import javax.swing.*;
import java.awt.*;

public class Knob extends JPanel {
    public Knob(String label, int min, int max) {
        setLayout(new BorderLayout());
        add(new JLabel(label), BorderLayout.NORTH);
        add(new RoundSlider(min, max));
    }
}
