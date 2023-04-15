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

        Graphics2D g2D = (Graphics2D) g;
        int pad = 5;
        int scaleD = 5;
        int w = getWidth();
        int h = getHeight();
        int l = Math.min(w, h);
        g2D.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g.setColor(Color.DARK_GRAY);
        g.drawArc((w - l) / 2 + pad, (h - l) / 2 + pad, l - 2 * pad, l - 2 * pad, 315, 270);

        g.setColor(Color.BLACK);
        g.fillArc((w - l) / 2 + pad + scaleD, (h - l) / 2 + pad + scaleD, l - 2 * pad - 2 * scaleD, l - 2 * pad - 2 * scaleD, 0, 360);
        g.setColor(Color.GRAY);
        g2D.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g.drawArc((w - l) / 2 + pad + scaleD, (h - l) / 2 + pad + scaleD, l - 2 * pad - 2 * scaleD, l - 2 * pad - 2 * scaleD, 0, 360);


        int circleSquareW = l - 2 * pad - 2 * scaleD;
        int circleSquareX = (w - l) / 2 + pad + scaleD;
        int circleSquareY = (h - l) / 2 + pad + scaleD;
        int circleCenterX = circleSquareX + circleSquareW / 2;
        int circleCenterY = circleSquareY + circleSquareW / 2;
        int cursorW = 6;
        int cursorAngle = 0;
        int cursorX = (int) (Math.cos(Math.toRadians(cursorAngle)) * (circleSquareW / 2.0d - cursorW / 2.0d));
        int cursorY = (int) (Math.sin(Math.toRadians(cursorAngle)) * (circleSquareW / 2.0d - cursorW / 2.0d));
        g2D.setColor(Color.WHITE);
        g2D.fillArc(circleCenterX + cursorX - cursorW / 2, circleCenterY + cursorY - cursorW / 2, cursorW, cursorW, 0, 360);
    }
}
