package com.impllife.split.ui.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.impllife.split.R;
import com.impllife.split.ui.MainActivity;

public class ContactAddFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_add, container, false);
        MainActivity.getInstance().showHead();
        MainActivity.getInstance().setHeadTitle("New contact");

        return view;
    }
}