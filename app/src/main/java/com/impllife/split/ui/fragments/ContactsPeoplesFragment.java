package com.impllife.split.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.view.PeopleSetupView;
import com.impllife.split.ui.view.PeopleView;

import java.util.List;

public class ContactsPeoplesFragment extends NavFragment {
    private View view;
    private View btnNew;
    private LinearLayout listItems;
    private boolean isPeopleSetupShow;
    private List<People> allPeoples;
    private PeopleSetupView peopleSetupView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setInflater(inflater);
        view = inflater.inflate(R.layout.fragment_contacts_peoples, container, false);

        listItems = view.findViewById(R.id.list_items);
        peopleSetupView = new PeopleSetupView(inflater, listItems, this::hidePeopleSetup, this::addNewContact);

        btnNew = view.findViewById(R.id.btn_new);
        btnNew.setOnClickListener(v -> showOrHidePeopleSetup());

        loadAndDrawAllPeoples();
        return view;
    }

    private void showOrHidePeopleSetup() {
        if (!isPeopleSetupShow) {
            showPeopleSetup();
        } else {
            hidePeopleSetup();
        }
    }

    private void loadAndDrawAllPeoples() {
        runAsync(() -> {
            allPeoples = DataService.getInstance().getAllPeoples();
            view.post(this::updateView);
        });
    }

    private void updateView() {
        listItems.removeAllViews();
        for (People e : allPeoples) {
            PeopleView peopleView = new PeopleView(inflater, listItems, e);
            peopleView.setBtnEditAction(people -> {
                peopleSetupView.setPeople(people);
                peopleSetupView.init();
                showPeopleSetup();
            });
            peopleView.setPostDeleteAction(this::loadAndDrawAllPeoples);
            listItems.addView(peopleView.getRoot());
        }
    }

    private void showPeopleSetup() {
        btnNew.setRotation(45);
        if (!isPeopleSetupShow) {
            isPeopleSetupShow = true;
            listItems.addView(peopleSetupView.getRoot(), 0);
        }
    }

    private void hidePeopleSetup() {
        btnNew.setRotation(0);
        if (isPeopleSetupShow) {
            isPeopleSetupShow = false;
            listItems.removeViewAt(0);
            peopleSetupView.setPeople(null);
            peopleSetupView.init();
        }
    }

    private void addNewContact() {
        hidePeopleSetup();
        loadAndDrawAllPeoples();
    }
}