package com.impllife.split.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.service.ComService;

import java.util.List;

public class ContactsPeoplesFragment extends NavFragment {
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
        view.findViewById(R.id.btn_new).setOnClickListener(v -> {
            navController.navigate(R.id.fragment_contact_setup_people);
        });
        LinearLayout listItems = view.findViewById(R.id.list_items);
        new Thread(() -> {
            List<People> allPeoples = ComService.getInstance().getAllPeoples();
            view.post(() -> {
                allPeoples.forEach(e -> {
                    TextView name = new TextView(inflater.getContext());
                    name.setText(e.getPseudonym());
                    listItems.addView(name);
                });
            });
        }).start();
        return view;
    }
}