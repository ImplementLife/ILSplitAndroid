package com.impllife.split.ui.view;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.MainActivity;

import static java.util.concurrent.CompletableFuture.runAsync;

public class PeopleSetupView extends BaseView {
    private boolean isUpdate;
    private People people;
    private Runnable postCancelAct;
    private Runnable postOkAction;
    private EditText etName;

    public PeopleSetupView(LayoutInflater inflater, ViewGroup rootForThis) {
        super(inflater, R.layout.view_people_setup, rootForThis);
        init();
    }

    public PeopleSetupView(LayoutInflater inflater, ViewGroup rootForThis, People people) {
        this(inflater, rootForThis);
        this.setPeople(people);
        init();
    }

    public void fillData() {
        if (isUpdate) {
            etName.setText(people.getPseudonym());
        } else {
            etName.setText("");
        }
    }

    public void init() {
        etName = findViewById(R.id.et_pseudonym);
        etName.postDelayed(this::focusKeyboard,50);

        fillData();

        View btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(v -> {
            MainActivity.getInstance().hideKeyboard();
            Editable text = etName.getText();
            if ("".equals(text.toString())) return;
            if (!isUpdate) people = new People();
            people.setPseudonym(text.toString());
            people.setDateUpdate(new Date());
            runAsync(() -> DataService.getInstance().save(people));

            if (postOkAction != null) postOkAction.run();
        });

        View btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> {
            MainActivity.getInstance().hideKeyboard();
            if (postCancelAct != null) postCancelAct.run();
        });
    }

    public void focusKeyboard() {
        MainActivity.getInstance().showKeyboard(etName);
    }

    public People getPeople() {
        return people;
    }
    public void setPeople(People people) {
        this.people = people;
        this.isUpdate = people != null;
    }

    public void setPostCancelAct(Runnable postCancelAct) {
        this.postCancelAct = postCancelAct;
    }
    public void setPostOkAction(Runnable postOkAction) {
        this.postOkAction = postOkAction;
    }
}
