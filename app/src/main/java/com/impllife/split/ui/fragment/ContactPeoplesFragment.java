package com.impllife.split.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.view.PeopleSetupView;
import com.impllife.split.ui.view.PeopleView;

import java.util.List;

public class ContactPeoplesFragment extends NavFragment {
    private final DataService dataService = DataService.getInstance();
    private View btnNew;
    private LinearLayout listItems;
    private boolean isPeopleSetupShow;
    private List<People> allPeoples;
    private PeopleSetupView peopleSetupView;
    private int peopleSetupViewPosition;
    private PeopleView hiddenPeopleView;

    public ContactPeoplesFragment() {
        super(R.layout.fragment_contact_people_list, false);
    }

    @Override
    protected void init() {
        listItems = findViewById(R.id.list_items);
        peopleSetupView = new PeopleSetupView(inflater, listItems);
        peopleSetupView.setPostOkAction(this::addNewContact);

        btnNew = findViewById(R.id.btn_new);
        btnNew.setOnClickListener(v -> {
            peopleSetupView.setPostCancelAct(this::hidePeopleSetup);
            showOrHidePeopleSetup();
        });

        loadAndDrawAllPeoples();
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
            allPeoples = dataService.getAllPeoples();
            post(this::updateView);
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
            peopleSetupView.focusKeyboard();
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
        post(this::hidePeopleSetup);
        loadAndDrawAllPeoples();
    }
}