package com.github.cbuschka.striketool.ui.conf;

import com.github.cbuschka.striketool.core.player.SamplePlayer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SamplePlayerConfig {

    @Bean(destroyMethod = "stop", initMethod = "start")
    public SamplePlayer samplePlayer() {
        return new SamplePlayer();
    }
}
