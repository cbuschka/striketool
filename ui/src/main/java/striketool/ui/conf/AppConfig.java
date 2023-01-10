package striketool.ui.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import striketool.ui.dialogs.app_window.AppWorker;

@Configuration
public class AppConfig {
    @Bean(destroyMethod = "stop", initMethod = "start")
    public AppWorker appWorker() {
        return new AppWorker();
    }
}
