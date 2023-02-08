package com.impllife.split.ui.fragments;

import android.view.View;

import java.util.function.Consumer;

public enum MainMenuButtonDefinition {
    BTN_CONTACTS("contacts", 1, 1, v -> {

    });

    MainMenuButtonDefinition(String name, int defaultCol, int defaultRow, Consumer<View> action) {
        this.name = name;
        this.action = action;
        this.defaultCol = defaultCol;
        this.defaultRow = defaultRow;
    }

    private final int defaultCol;
    private final int defaultRow;
    private String name;
    private Consumer<View> action;

    public int getDefaultCol() {
        return defaultCol;
    }
    public int getDefaultRow() {
        return defaultRow;
    }
    public String getName() {
        return name;
    }
    public Consumer<View> getAction() {
        return action;
    }
}
