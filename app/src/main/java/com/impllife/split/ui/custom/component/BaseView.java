package com.impllife.split.ui.custom.component;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.impllife.split.ui.MainActivity;

public class BaseView {
    protected View root;
    protected LayoutInflater inflater;

    public BaseView(LayoutInflater inflater, int viewId, ViewGroup rootForThis) {
        this.inflater = inflater;
        this.root = inflater.inflate(viewId, rootForThis, false);
    }

    public BaseView(int viewId, ViewGroup rootForThis) {
        this(LayoutInflater.from(MainActivity.getInstance()), viewId, rootForThis);
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

    public void setTextViewById(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        getRoot().setOnClickListener(listener);
    }

    public void setImgResById(int viewId, int resId) {
        ((ImageView) findViewById(viewId)).setImageResource(resId);
    }
}
