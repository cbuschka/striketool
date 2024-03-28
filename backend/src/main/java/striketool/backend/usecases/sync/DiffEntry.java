package striketool.backend.usecases.sync;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import striketool.backend.module.DrumModuleAdapter;
import striketool.backend.module.Type;
import striketool.backend.repository.Repository;

@Getter
@Setter
@AllArgsConstructor
public class DiffEntry {
    private Type type;
    private final String name;
    private boolean internal;
    private State state;
    private DrumModuleAdapter.Entry drumModuleEntry;
    private Repository.Entry repositoryEntry;

    public String name(int i) {
        String[] parts = name.split("/");
        if( i<parts.length) {
            return parts[i];
        }
        return "unknown";
    }
}
