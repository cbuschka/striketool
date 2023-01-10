package striketool.ui.dialogs.session_window;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {

    private final JLabel statusLabel;

    public StatusBar() {
        setLayout(new BorderLayout());
        statusLabel = new JLabel("");
        add(statusLabel, BorderLayout.CENTER);
    }

    public void setMessage(String message) {
        statusLabel.setText(message);
    }
}
