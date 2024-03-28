package striketool.backend.repository;

import java.io.File;

public class Repository {

    private FsBlobStore fsBlobStore;

    public void start() {
        File homeDir = new File(System.getProperty("user.home"));
        this.fsBlobStore = new FsBlobStore(new File(new File(new File(homeDir, ".striketool"), "repository"), "v1"));
    }

    public void stop() {
    }

    interface Entry {
        String name();

        int versions();
    }

    public Iterable<Entry> listInstruments() {
        return fsBlobStore.list("Instruments");
    }

    public Iterable<Entry> listSample() {
        return fsBlobStore.list("Samples");
    }

    public Iterable<Entry> listKits() {
        return fsBlobStore.list("Kits");
    }

    public Iterable<Entry> listLoops() {
        return fsBlobStore.list("Loops");
    }
}
