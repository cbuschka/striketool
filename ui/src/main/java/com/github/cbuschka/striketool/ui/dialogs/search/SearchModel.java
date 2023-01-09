package com.github.cbuschka.striketool.ui.dialogs.search;

import java.util.ArrayList;
import java.util.List;

public class SearchModel {

    private boolean searchInProgress = false;

    private List<Listener> listeners = new ArrayList<>();

    public interface Listener {
        void modelChanged();
    }

    public void addListener(Listener l) {
        this.listeners.add(l);
    }

    private void fireModelChanged() {
        for (Listener l : listeners) {
            l.modelChanged();
        }
    }

    public void setSearchInProgress(boolean searchInProgress) {
        this.searchInProgress = searchInProgress;
        fireModelChanged();
    }

    public boolean isSearchInProgress() {
        return searchInProgress;
    }
}
