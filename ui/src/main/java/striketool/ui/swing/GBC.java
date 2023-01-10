package striketool.ui.swing;

import java.awt.*;

public class GBC {

    public static Builder builder() {
        return new Builder();
    }

    public static Builder at(int x, int y) {
        return new Builder().at(x, y);
    }


    public static Builder dim(int w, int h) {
        return new Builder().dim(w, h);
    }

    public static Builder weight(double wx, double wy) {
        return new Builder().weightx(wx).weighty(wy);
    }

    public static class Builder {
        private GridBagConstraints gridBagConstraints = new GridBagConstraints();

        private Builder() {
            gridBagConstraints.gridx = GridBagConstraints.RELATIVE;
            gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
            gridBagConstraints.fill = GridBagConstraints.NONE;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.gridheight = 1;
            gridBagConstraints.weightx = 0.5d;
            gridBagConstraints.weighty = 0.5d;
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
        }

        public Builder fillHoriz() {
            if (gridBagConstraints.fill == GridBagConstraints.VERTICAL)
                gridBagConstraints.fill = GridBagConstraints.BOTH;
            else
                gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            return this;
        }

        public Builder fillVert() {
            if (gridBagConstraints.fill == GridBagConstraints.HORIZONTAL)
                gridBagConstraints.fill = GridBagConstraints.BOTH;
            else
                gridBagConstraints.fill = GridBagConstraints.VERTICAL;
            return this;
        }

        public Builder weight(double wx, double wy) {
            return weightx(wx).weighty(wy);
        }

        public Builder at(int x, int y) {
            return gridx(x).gridy(y);
        }

        public Builder dim(int w, int h) {
            return gridw(w).gridh(h);
        }

        public Builder gridx(int x) {
            gridBagConstraints.gridx = x;
            return this;
        }

        public Builder gridy(int y) {
            gridBagConstraints.gridy = y;
            return this;
        }


        public Builder gridw(int w) {
            gridBagConstraints.gridwidth = w;
            return this;
        }

        public Builder gridh(int h) {
            gridBagConstraints.gridheight = h;
            return this;
        }


        public Builder gridwRemainder() {
            gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
            return this;
        }

        public Builder gridhRemainder() {
            gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
            return this;
        }

        public Builder weightx(double w) {
            gridBagConstraints.weightx = w;
            return this;
        }

        public Builder weighty(double w) {
            gridBagConstraints.weighty = w;
            return this;
        }


        public Builder anchorPageStart() {
            gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
            return this;
        }

        public Builder anchorPageEnd() {
            gridBagConstraints.anchor = GridBagConstraints.PAGE_END;
            return this;
        }


        public Builder anchorCenter() {
            gridBagConstraints.anchor = GridBagConstraints.CENTER;
            return this;
        }

        public Builder anchorFirstLineStart() {
            gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
            return this;
        }

        public Builder anchorFirstLineEnd() {
            gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
            return this;
        }

        public Builder anchorLineStart() {
            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            return this;
        }

        public Builder anchorLineEnd() {
            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            return this;
        }

        public GridBagConstraints build() {
            return gridBagConstraints;
        }

        public Builder fillBoth() {
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            return this;
        }
    }
}
