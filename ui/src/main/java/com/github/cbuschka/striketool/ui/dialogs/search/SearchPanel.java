package com.github.cbuschka.striketool.ui.dialogs.search;

import com.github.cbuschka.striketool.ui.swing.EDTAssert;
import com.github.cbuschka.striketool.ui.swing.GBC;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {

    private JTextField searchPhraseTextField;
    private JButton findButton;
    private JList<SearchModel.Item> resultList;

    public SearchPanel() {
        EDTAssert.check();

        setLayout(new GridBagLayout());
        searchPhraseTextField = new JTextField("Search...");
        add(searchPhraseTextField, GBC.at(0, 0).fillBoth().weightx(0.9d).anchorFirstLineStart().build());
        findButton = new JButton("Find");
        add(findButton, GBC.at(1, 0).fillBoth().weightx(0.1d).gridwRemainder().anchorFirstLineEnd().build());
        resultList = new JList<>();
        resultList.setCellRenderer((jList, item, index, isSelected, cellHasFocus) -> new JLabel(item.label));
        add(resultList, GBC.at(0, 1).gridw(2).weighty(100.0d).gridhRemainder().fillBoth().anchorPageEnd().build());
    }

    public String getSearchText() {
        EDTAssert.check();

        return searchPhraseTextField.getText();
    }

    public void watch(SearchModel model) {
        EDTAssert.check();

        this.resultList.setModel(model.getResultModel());
    }

    public JButton getFindButton() {
        return findButton;
    }
}
