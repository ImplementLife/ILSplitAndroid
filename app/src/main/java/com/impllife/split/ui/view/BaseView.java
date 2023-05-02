package com.impllife.split.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseView {
    protected View root;
    protected LayoutInflater inflater;

    public BaseView(LayoutInflater inflater, int viewId, ViewGroup rootForThis) {
        this.inflater = inflater;
        this.root = inflater.inflate(viewId, rootForThis, false);

    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public View getRoot() {
        return root;
    }
    protected void setRoot(View root) {
        this.root = root;
    }

    public boolean post(Runnable action) {
        return root.post(action);
    }

    public <T extends View> T findViewById(int id) {
        return root.findViewById(id);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        getRoot().setOnClickListener(listener);
    }
}
