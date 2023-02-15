package com.impllife.split.ui.view;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.service.DataService;

import static java.util.concurrent.CompletableFuture.runAsync;

public class PeopleSetupView extends BaseView {
    private boolean isUpdate;
    private People people;
    private Runnable postCancelAct;
    private Runnable postOkAction;

    public PeopleSetupView(LayoutInflater inflater, ViewGroup rootForThis, Runnable postCancelAct, Runnable postOkAction) {
        super(inflater, R.layout.view_people_setup, rootForThis);
        this.postCancelAct = postCancelAct;
        this.postOkAction = postOkAction;
        init();
    }

    public PeopleSetupView(LayoutInflater inflater, ViewGroup rootForThis, Runnable postCancelAct, Runnable postOkAction, People people) {
        this(inflater, rootForThis, postCancelAct, postOkAction);
        this.setPeople(people);
        init();
    }

    public void init() {
        EditText etName = findViewById(R.id.et_pseudonym);
        if (isUpdate) {
            etName.setText(people.getPseudonym());
        } else {
            etName.setText("");
        }

        View btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(v -> {
            Editable text = etName.getText();
            if ("".equals(text.toString())) return;
            if (!isUpdate) {
                people = new People();
                runAsync(() -> {
                    people.setPseudonym(text.toString());
                    DataService.getInstance().insert(people);
                });
            } else {
                runAsync(() -> {
                    people.setPseudonym(text.toString());
                    DataService.getInstance().update(people);
                });
            }
            postOkAction.run();
        });

        View btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setRotation(45);
        btnCancel.setOnClickListener(v -> postCancelAct.run());
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
        this.isUpdate = people != null;
    }
}
