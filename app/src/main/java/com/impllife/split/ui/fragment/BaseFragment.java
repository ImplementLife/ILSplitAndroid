package com.impllife.split.ui.fragment;

import android.util.Log;
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
        CompletableFuture.runAsync(() -> {
            try {
                runnable.run();
            } catch (Throwable t) {
                Log.e("runAsync", "", t);
            }
        });
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

    public boolean post(Runnable action) {
        boolean result = root.post(action);
        if (!result) Log.e("post", "task doesn't performed complete");
        return result;
    }

    public boolean postDelayed(Runnable action, long delayMillis) {
        boolean result = root.postDelayed(action, delayMillis);
        if (!result) Log.e("postDelayed", "task doesn't performed complete");
        return result;
    }
}
