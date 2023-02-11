package com.impllife.split.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.impllife.split.R;

public class ContactSetupPeopleFragment extends NavFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_setup_people, container, false);
        setNavTitle("New contact");

        return view;
    }
}