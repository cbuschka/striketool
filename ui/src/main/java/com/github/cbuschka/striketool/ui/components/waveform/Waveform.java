package com.github.cbuschka.striketool.ui.components.waveform;

import javax.swing.*;
import java.awt.*;

public class Waveform extends JComponent {

    private WaveformModel model;

    public Waveform() {
    }

    public void setModel(WaveformModel model) {
        this.model = model;
    }

    public Waveform(WaveformModel model) {
        setModel(model);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        int width = getWidth();
        int height = getHeight();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        if (this.model != null) {
            g.setColor(Color.GREEN);
            double xStep = (double) this.model.getLength() / width;
            for (int x = 0; x < width; ++x) {
                int index = (int) (x * xStep);
                double level = this.model.getValue(index);
                level = Math.signum(level) * Math.min(1, Math.abs(level));
                int y = (int) (height / 2 * level);
                g.drawLine(x, height / 2 - y, x, height / 2 + y);
            }
        }
    }
}
