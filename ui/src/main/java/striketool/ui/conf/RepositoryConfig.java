package striketool.ui.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import striketool.backend.module.DrumModuleService;
import striketool.backend.module.Simulator;
import striketool.backend.repository.Repository;

import java.util.Arrays;

@Configuration
@EnableScheduling
public class RepositoryConfig {

    @Bean(destroyMethod = "stop", initMethod = "start")
    public Repository repositoryService() {
        return new Repository();
    }
}
