package striketool.ui.dialogs.session_window;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.RescaleOp;

public class ImagePane extends JPanel {

    private BufferedImage image;
    private BufferedImage disabledImage;
    private boolean enabled = false;

    @SneakyThrows
    public ImagePane(String resource) {
        image = ImageIO.read(getClass().getResourceAsStream(resource));

        createDisabledImage();
    }

    private void createDisabledImage() {
        RescaleOp rescaleOp = new RescaleOp(0.3f, 15, null);
        disabledImage = rescaleOp.filter(image, null);
        BufferedImageOp grayscaleOp = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        disabledImage = grayscaleOp.filter(disabledImage, disabledImage);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            repaint();
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    private Dimension calcImageWidth() {
        double wFactor = getWidth() / (double) image.getWidth();
        double hFactor = getHeight() / (double) image.getHeight();
        if (wFactor < hFactor) {
            return new Dimension((int) (image.getWidth() * wFactor), (int) (image.getHeight() * wFactor));
        } else {
            return new Dimension((int) (image.getWidth() * hFactor), (int) (image.getHeight() * hFactor));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension imageDim = calcImageWidth();
        int componentW = getWidth();
        int componentH = getHeight();

        int x = (componentW - imageDim.width) / 2;
        int y = (componentH - imageDim.height) / 2;


        if (enabled) {
            g.drawImage(this.image, x, y, imageDim.width, imageDim.height, this);
        } else {
            g.drawImage(this.disabledImage, x, y, imageDim.width, imageDim.height, this);
        }
    }
}