package com.impllife.split.ui.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.impllife.split.R;

public class ContactsPeoplesFragment extends Fragment {
    public ContactsPeoplesFragment() {
    }

    public static ContactsPeoplesFragment newInstance() {
        ContactsPeoplesFragment fragment = new ContactsPeoplesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_peoples, container, false);

        return view;
    }
}