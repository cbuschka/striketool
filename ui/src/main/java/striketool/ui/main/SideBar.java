package striketool.ui.main;

import striketool.ui.lib.LabelPanel;

import javax.swing.*;
import java.awt.*;

public class SideBar extends JPanel {

    private JTree tree;

    public SideBar() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(new JTextField(10), BorderLayout.CENTER);
        searchPanel.add(new JButton("Go"), BorderLayout.EAST);
        add(new LabelPanel<>("Search", searchPanel), BorderLayout.NORTH);


        this.tree = new JTree();
        add(this.tree, BorderLayout.CENTER);
    }
}
