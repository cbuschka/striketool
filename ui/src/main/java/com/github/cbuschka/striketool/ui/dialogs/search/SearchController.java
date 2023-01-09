package com.github.cbuschka.striketool.ui.dialogs.search;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchController {

    private DefaultListModel<Item> resultModel = new DefaultListModel<>();
    private SearchModel model;

    public SearchController(SearchPanel view, SearchModel model) {
        this.model = model;
        JButton findButton = view.getFindButton();
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startSearch();
            }
        });

        model.addListener(new SearchModel.Listener() {
            @Override
            public void modelChanged() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        updateView();
                    }
                });
            }
        });
    }

    private void updateView() {
    }

    private void startSearch() {
        // cancel pending searhch?
        // mark model as searching
        // strat search async

        // mark model search finished
    }
}
