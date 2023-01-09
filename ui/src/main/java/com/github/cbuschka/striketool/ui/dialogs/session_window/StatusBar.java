package com.github.cbuschka.striketool.ui.dialogs.session_window;

import com.github.cbuschka.striketool.core.alesis.drum_module.DrumModule;
import com.github.cbuschka.striketool.core.alesis.drum_module.DrumModuleEvent;
import com.github.cbuschka.striketool.core.alesis.drum_module.DrumModuleListener;
import com.github.cbuschka.striketool.ui.Session;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {

    public StatusBar(Session session) {
        setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel("");
        add(statusLabel, BorderLayout.CENTER);

        DrumModule drumModule = session.getDrumModule();
        drumModule.addListener(new DrumModuleListener() {
            @Override
            public void available(DrumModuleEvent event) {
                SwingUtilities.invokeLater(() -> statusLabel.setText("available"));
            }

            @Override
            public void unavailable(DrumModuleEvent event) {
                SwingUtilities.invokeLater(() -> statusLabel.setText("unavailable"));
            }
        });
        statusLabel.setText(drumModule.isAvailable() ? "available" : "unavailable");
    }
}
