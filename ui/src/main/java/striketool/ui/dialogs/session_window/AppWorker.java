package striketool.ui.dialogs.session_window;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class AppWorker {

    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private volatile Thread workerThread;

    public void runAsync(Runnable r) {
        this.queue.add(r);
    }

    public synchronized void start() {
        if (workerThread != null) {
            return;
        }

        workerThread = new Thread(this::workerMain);
        workerThread.start();
    }

    public synchronized void stop() {
        Thread workerThread = this.workerThread;
        this.workerThread = null;
        if (workerThread != null) {
            workerThread.interrupt();
        }
    }

    private void workerMain() {
        while (Thread.currentThread() == workerThread
                && !Thread.currentThread().isInterrupted()) {
            try {
                Runnable job = queue.take();
                job.run();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } catch (Exception ex) {
                log.error("Failed.", ex);
            }
        }
    }


}
