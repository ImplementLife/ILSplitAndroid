package com.impllife.split.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import java.util.concurrent.CompletableFuture;

public class BaseFragment extends Fragment {
    protected LayoutInflater inflater;
    protected View root;

    protected void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    protected void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(runnable);
    }

    public View createView(int layout, LayoutInflater inflater, ViewGroup container) {
        this.inflater = inflater;
        View view = inflater.inflate(layout, container, false);
        root = view;
        return view;
    }

    public void updateView(Runnable runnable) {
        getView().post(runnable);
    }

    protected <T extends View> T findViewById(int id) {
        return getView().findViewById(id);
    }

    /**
     *
     * @return root view
     * @see #createView(int, LayoutInflater, ViewGroup)
     */
    @Override
    public View getView() {
        View view = super.getView();
        if (view == null) view = root;
        return view;
    }
}
