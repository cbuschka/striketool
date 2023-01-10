package striketool.ui.dialogs.app_window;

public class Barrier {
    private final Object LOCK = new Object();
    private int count = 0;

    public void add() {
        synchronized (LOCK) {
            this.count++;
        }
    }

    public void free() {
        synchronized (LOCK) {
            this.count--;
            LOCK.notifyAll();
        }
    }

    public void waitFor() throws InterruptedException {
        synchronized (LOCK) {
            while (count > 0) {
                LOCK.wait(100);
            }
        }
    }
}
