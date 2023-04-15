package striketool.ui.main;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

@Slf4j
public class StatusPanel extends JPanel {
    public StatusPanel() {
        setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
        setLayout(new BorderLayout());
        add(new JLabel("It's all ok."), BorderLayout.CENTER);
    }
}
