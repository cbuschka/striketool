package striketool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StriketoolMain {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", Boolean.FALSE.toString());
        SpringApplication.run(StriketoolMain.class, args);
    }
}
