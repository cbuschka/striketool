package com.github.cbuschka.striketool.ui.conf;

import com.github.cbuschka.striketool.core.alesis.drum_module.DrumModule;
import com.github.cbuschka.striketool.core.alesis.drum_module.simulator.Simulator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DrumModuleConfig {

    @Bean(destroyMethod = "stop", initMethod = "start")
    public DrumModule drumModule() {
        return new DrumModule(new Simulator());
    }
}
