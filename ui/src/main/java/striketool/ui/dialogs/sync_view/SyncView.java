package striketool.ui.dialogs.sync_view;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class SyncView extends JPanel {

    private final SyncStateModel model;

    public SyncView(SyncStateModel model) {
        this.model = model;
        setLayout(new BorderLayout());

        JTree tree = new JTree();
        DefaultTreeCellRenderer defaultTreeCellRenderer = new DefaultTreeCellRenderer();
        tree.setCellRenderer(new TreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                          boolean selected, boolean expanded, boolean leaf, int row,
                                                          boolean hasFocus) {
                Component returnValue = null;
                if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
                    Object userObject = ((DefaultMutableTreeNode) value)
                            .getUserObject();
                    /*
                    if (userObject instanceof YourClass) {
                        YourClass yourElement = (YourClass) userObject;
                        if (col == 0) titleLabel.setText(yourElement.getID());
                        if (col == 1) titleLabel.setText(yourElement.getName());
                        if (col == 2) titleLabel.setText(yourElement.getParentID());
                        if (selected) {
                            renderer.setBackground(backgroundSelectionColor);
                        } else {
                            renderer.setBackground(backgroundNonSelectionColor);
                        }
                        renderer.setEnabled(tree.isEnabled());
                        returnValue = renderer;
                    }
                     */
                }
                if (returnValue == null) {
                    returnValue = defaultTreeCellRenderer.getTreeCellRendererComponent(tree,
                            value, selected, expanded, leaf, row, hasFocus);
                }
                return returnValue;
            }
        });
        tree.setModel(model);

        add(tree, BorderLayout.CENTER);
    }
}