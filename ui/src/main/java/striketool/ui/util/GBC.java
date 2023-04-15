package striketool.ui.util;

import java.awt.*;

public class GBC {
    private int gridx, gridy, gridw = 1, gridh = 1;
    private double weightx = 1.0d, weighty = 1.0d;
    private int anchor = GridBagConstraints.CENTER;
    private int fill = GridBagConstraints.BOTH;
    private int ipadx = 0;
    private int ipady = 0;
    private Insets insets = new Insets(0, 0, 0, 0);

    public static GBC at(int gridx, int gridy) {
        GBC gbc = new GBC();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        return gbc;
    }

    public GBC gridw(int gridw) {
        this.gridw = gridw;
        return this;
    }

    public GBC gridh(int gridh) {
        this.gridh = gridh;
        return this;
    }

    public GBC size(int gridw, int gridh) {
        this.gridw = gridw;
        this.gridh = gridh;
        return this;
    }

    public GBC weightx(double weightx) {
        this.weightx = weightx;
        return this;
    }


    public GBC weighty(double weighty) {
        this.weighty = weighty;
        return this;
    }


    public GBC anchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    public GBC fill(int fill) {
        this.fill = fill;
        return this;
    }

    public GBC noFill() {
        this.fill = GridBagConstraints.NONE;
        return this;
    }

    public GridBagConstraints build() {
        return new GridBagConstraints(gridx, gridy, gridw, gridh, weightx, weighty, anchor, fill, insets, ipadx, ipady);
    }
}
