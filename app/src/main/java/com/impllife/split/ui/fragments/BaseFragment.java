package com.impllife.split.ui.fragments;

import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;

import java.util.concurrent.CompletableFuture;

public class BaseFragment extends Fragment {
    protected LayoutInflater inflater;

    protected void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    protected void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(runnable);
    }
}
