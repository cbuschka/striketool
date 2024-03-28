package striketool.ui.dialogs.sync_view;

import lombok.extern.slf4j.Slf4j;
import striketool.backend.usecases.sync.DiffEntry;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

@Slf4j
public class SyncStateModel extends DefaultTreeModel {

    private DefaultMutableTreeNode root = new DefaultMutableTreeNode();
    private DefaultMutableTreeNode instruments = new DefaultMutableTreeNode("Instruments");
    private DefaultMutableTreeNode samples = new DefaultMutableTreeNode("Samples");
    private DefaultMutableTreeNode kits = new DefaultMutableTreeNode("Kits");
    private DefaultMutableTreeNode loops = new DefaultMutableTreeNode("Loops");

    public SyncStateModel() {
        super(null, false);
        setRoot(root);

        root.add(instruments);
        root.add(samples);
        root.add(kits);
        root.add(loops);
    }

    public void add(List<DiffEntry> diffEntries) {
        for (DiffEntry diffEntry : diffEntries) {
            boolean internal = diffEntry.isInternal();
            switch (diffEntry.getType()) {
                case SAMPLE:
                    addIfMissing(samples, diffEntry.getName(), internal);
                    break;
                case INSTRUMENT:
                    addIfMissing(instruments, diffEntry.getName(), internal);
                    break;
                case KIT:
                    addIfMissing(kits, diffEntry.getName(), internal);
                    break;
                case LOOP:
                    addIfMissing(loops, diffEntry.getName(), internal);
                    break;
                default:
                    break;
            }
        }
    }

    private void addIfMissing(DefaultMutableTreeNode parent, String path, boolean internal) {
        String[] folders = path.split("/");

        DefaultMutableTreeNode curr = parent;
        for (String folder : folders) {
            curr = getOrCreateChild(folder, curr);
        }
    }

    private static DefaultMutableTreeNode getOrCreateChild(String folder, DefaultMutableTreeNode curr) {
        for (int i = 0; i < curr.getChildCount(); ++i) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) curr.getChildAt(i);
            if (folder.trim().equals(child.getUserObject())) {
                return child;
            }
        }

        DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(folder.trim());
        curr.add(newChild);
        return newChild;
    }
}
