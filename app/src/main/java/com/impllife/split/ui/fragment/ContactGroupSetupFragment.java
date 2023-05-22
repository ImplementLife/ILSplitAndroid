package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.impllife.split.R;
import com.impllife.split.ui.custom.component.NavFragment;

public class ContactGroupSetupFragment extends NavFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_group_setup, container, false);
        setNavTitle("New group");

        return view;
    }
}