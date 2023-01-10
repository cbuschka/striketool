package striketool.ui.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import striketool.ui.dialogs.session_window.AppWindow;
import striketool.ui.dialogs.session_window.AppWorker;

@Configuration
public class AppConfig {
    @Bean(destroyMethod = "stop", initMethod = "start")
    public AppWorker appWorker() {
        return new AppWorker();
    }
}
