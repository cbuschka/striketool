package striketool.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import striketool.ui.main.MainController;

import javax.swing.*;
import java.io.File;

@Slf4j
@SpringBootApplication
public class Main {
    public static void main(String[] args) throws InterruptedException {
        MainController mainController = new MainController();
        SwingUtilities.invokeLater(mainController::open);
        SwingUtilities.invokeLater(() -> {
            mainController.openInstrumentFile(new File("/home/conni/work/conni/github/cbuschka/strike4j/src/test/resources/NewHHCymbal5x1.sin"));
        });

        log.info("Waiting for main window to close.");
        mainController.waitFor();
        System.exit(0);
    }
}
