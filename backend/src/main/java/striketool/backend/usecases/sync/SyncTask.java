package striketool.backend.usecases.sync;

import lombok.AllArgsConstructor;
import striketool.backend.module.DrumModuleAdapter;
import striketool.backend.module.Type;
import striketool.backend.repository.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SyncTask implements Runnable {

    private final DrumModuleAdapter drumModuleAdapter;
    private final Repository repository;
    private final Handler handler;

    @Override
    public void run() {
        diffSamples();
    }

    private void diffSamples() {
        Map<String, DiffEntry> entryMap = new HashMap<>();
        for (DrumModuleAdapter.Entry entry : drumModuleAdapter.listSamples()) {
            entryMap.put(entry.getName(), new DiffEntry(Type.SAMPLE, entry.getName(), true, null, entry, null));
        }

        for (Repository.Entry entry : repository.listSample()) {
            DiffEntry diffEntry = entryMap.computeIfAbsent(entry.getName(),
                    (name) -> new DiffEntry(Type.SAMPLE, name, true, null, null, entry));
            entryMap.put(entry.getName(), diffEntry);
        }

        List<DiffEntry> diffEntries = new ArrayList<>();
        for (Map.Entry<String, DiffEntry> entry : entryMap.entrySet()) {
            DiffEntry diffEntry = entry.getValue();
            if (diffEntry.getDrumModuleEntry() == null) {
                diffEntry.setState(State.DELETED_ON_DRUM_MODULE);
            } else if (diffEntry.getRepositoryEntry() == null) {
                diffEntry.setState(State.ADDED_ON_DRUM_MODULE);
            } else {
                diffEntry.setState(State.OK);
            }
            diffEntries.add(diffEntry);
        }
        handler.diffSeen(diffEntries);
    }

    public interface Handler {
        void diffSeen(List<DiffEntry> diffEntries);
    }
}