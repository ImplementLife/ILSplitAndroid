package com.impllife.split.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.service.ComService;

public class ContactSetupPeopleFragment extends NavFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_setup_people, container, false);
        setNavTitle("New contact");

        view.findViewById(R.id.btn_ok).setOnClickListener(v -> {
            Editable text = ((EditText) view.findViewById(R.id.et_pseudonym)).getText();
            People people = new People();
            people.setPseudonym(text.toString());
            new Thread(() -> {
                ComService.getInstance().insert(people);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
                view.post(() -> {
                    navController.navigateUp();
                });
            }).start();

            //navController.navigateUp();
        });

        return view;
    }
}