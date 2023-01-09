package com.github.cbuschka.striketool.ui;

import com.github.cbuschka.striketool.core.alesis.drum_module.DrumModule;
import com.github.cbuschka.striketool.core.player.SamplePlayer;
import com.github.cbuschka.striketool.ui.dialogs.session_window.SessionWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class AppMain implements CommandLineRunner, Session {
    @Autowired
    private SamplePlayer samplePlayer;
    @Autowired
    private DrumModule drumModule;

    @Override
    public DrumModule getDrumModule() {
        return drumModule;
    }

    @Override
    public SamplePlayer getSamplePlayer() {
        return samplePlayer;
    }

    @Override
    public void run(String... args) throws Exception {

        SessionWindow sessionWindow = new SessionWindow(this);
        sessionWindow.open();

        SessionWindow.waitForAllClosed();
    }
}
