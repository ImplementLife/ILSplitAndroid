package com.impllife.split.ui.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.impllife.split.R;

public class ContactsGroupsFragment extends Fragment {
    public ContactsGroupsFragment() {
    }

    public static ContactsGroupsFragment newInstance() {
        ContactsGroupsFragment fragment = new ContactsGroupsFragment();
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
        View view = inflater.inflate(R.layout.fragment_contacts_groups, container, false);
        view.findViewById(R.id.btn_new).setOnClickListener(v-> {
            NavHostFragment.findNavController(this).navigate(R.id.fragment_contact_setup_group);
        });
        return view;
    }
}