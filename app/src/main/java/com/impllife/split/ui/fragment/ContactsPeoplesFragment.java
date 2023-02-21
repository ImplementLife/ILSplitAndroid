package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.MainActivity;
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
    private int peopleSetupViewPosition;
    private PeopleView hiddenPeopleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setInflater(inflater);
        view = inflater.inflate(R.layout.fragment_contacts_peoples, container, false);

        listItems = view.findViewById(R.id.list_items);
        peopleSetupView = new PeopleSetupView(inflater, listItems);
        peopleSetupView.setPostOkAction(this::addNewContact);

        btnNew = view.findViewById(R.id.btn_new);
        btnNew.setOnClickListener(v -> {
            peopleSetupView.setPostCancelAct(this::hidePeopleSetup);
            peopleSetupView.focusKeyboard();
            showOrHidePeopleSetup();
        });

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
        for (int i = 0; i < allPeoples.size(); i++) {
            PeopleView peopleView = new PeopleView(inflater, listItems, allPeoples.get(i));
            int position = i;
            peopleView.setBtnEditAction(people -> {
                if (isPeopleSetupShow) {
                    hidePeopleSetup();
                }
                peopleSetupViewPosition = position;
                peopleSetupView.setPeople(people);
                peopleSetupView.setPostCancelAct(() -> {
                    hidePeopleSetup(position);
                    peopleView.getRoot().setVisibility(View.VISIBLE);
                });
                peopleView.getRoot().setVisibility(View.GONE);
                hiddenPeopleView = peopleView;
                showPeopleSetup(position);
            });
            peopleView.setPostDeleteAction(this::loadAndDrawAllPeoples);
            listItems.addView(peopleView.getRoot());
        }
    }

    private void showPeopleSetup() {
        peopleSetupViewPosition = 0;
        showPeopleSetup(0);
    }
    private void showPeopleSetup(int position) {
        btnNew.setRotation(45);
        if (!isPeopleSetupShow) {
            isPeopleSetupShow = true;
            peopleSetupView.fillData();
            listItems.addView(peopleSetupView.getRoot(), position);
        }
    }

    private void hidePeopleSetup() {
        hidePeopleSetup(peopleSetupViewPosition);
    }
    private void hidePeopleSetup(int position) {
        btnNew.setRotation(0);
        if (isPeopleSetupShow) {
            isPeopleSetupShow = false;
            MainActivity.getInstance().hideKeyboard();
            listItems.removeViewAt(position);
            if (hiddenPeopleView != null) hiddenPeopleView.getRoot().setVisibility(View.VISIBLE);
            peopleSetupView.setPeople(null);
        }
    }

    private void addNewContact() {
        hidePeopleSetup();
        loadAndDrawAllPeoples();
    }
}