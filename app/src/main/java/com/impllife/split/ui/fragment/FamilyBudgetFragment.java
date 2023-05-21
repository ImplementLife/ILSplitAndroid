package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.impllife.split.R;
import com.impllife.split.ui.custom.component.NavFragment;

public class FamilyBudgetFragment extends NavFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_budget, container, false);

        return view;
    }
}