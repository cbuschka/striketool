package com.github.cbuschka.striketool.ui;

import com.github.cbuschka.striketool.core.player.SamplePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Inventory {
    @Autowired
    private SamplePlayer samplePlayer;

    public SamplePlayer getSamplePlayer() {
        return samplePlayer;
    }
}
