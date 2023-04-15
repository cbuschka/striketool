package striketool.ui.lib;

import javax.swing.*;
import java.awt.*;

public class LabelPanel<T extends JComponent> extends JPanel {
    public LabelPanel(String title, T component) {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), title));
        setLayout(new BorderLayout());
        add(component, BorderLayout.CENTER);
    }
}
