package com.github.cbuschka.striketool.ui;

import com.github.cbuschka.striketool.core.alesis.drum_module.DrumModule;
import com.github.cbuschka.striketool.core.player.SamplePlayer;

public interface Session {

    SamplePlayer getSamplePlayer();

    DrumModule getDrumModule();
}
