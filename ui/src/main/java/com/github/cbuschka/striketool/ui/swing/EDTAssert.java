package com.github.cbuschka.striketool.ui.swing;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class EDTAssert {

    public static void check() {
        if (!SwingUtilities.isEventDispatchThread()) {
            throw new IllegalStateException("Use EDT!");
        }
    }

    public static void runAsync(Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }


    public static void runSync(Runnable r) throws InterruptedException, InvocationTargetException {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeAndWait(r);
        }
    }
}
