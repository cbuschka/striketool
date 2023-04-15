package striketool.ui.instrument_editor;

import javax.swing.*;
import java.awt.*;

public class RoundSlider extends JComponent {
    public RoundSlider(int min, int max) {
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int pad = 5;
        int w = getWidth();
        int h = getHeight();
        int l = Math.min(w, h);
        g.setColor(Color.BLACK);
        g.fillArc((w - l) / 2 + pad, (h - l) / 2 + pad, l - 2 * pad, l - 2 * pad, 0, 360);
    }
}
