package striketool.ui.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import striketool.backend.module.DrumModuleService;
import striketool.backend.module.Simulator;

import java.util.Arrays;

@Configuration
@EnableScheduling
public class DrumModuleConfig {

    @Bean(destroyMethod = "stop", initMethod = "start")
    public DrumModuleService drumModuleService() {
        return new DrumModuleService(Arrays.asList(simulator()));
    }

    @Bean
    public Simulator simulator() {
        return new Simulator();
    }
}
