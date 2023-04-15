package striketool.ui.util;

import javax.swing.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UIUtils {
    private static Executor executor = Executors.newFixedThreadPool(20);

    public static void runAsyncOnUIThread(Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    public static void runAsyncInBackground(Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            executor.execute(r);
        } else {
            r.run();
        }
    }
}
