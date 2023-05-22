package com.impllife.split.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.impllife.split.R;

public class ContactGroupListFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_group_list, container, false);
        view.findViewById(R.id.btn_new).setOnClickListener(v-> {
            NavHostFragment.findNavController(this).navigate(R.id.fragment_contact_group_setup);
        });
        return view;
    }
}