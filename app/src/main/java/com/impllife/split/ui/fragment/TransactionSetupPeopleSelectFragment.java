package com.impllife.split.ui.fragment;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.custom.component.BaseFragment;
import com.impllife.split.ui.dialog.SearchPeopleDialog;

import java.util.List;

public class TransactionSetupPeopleSelectFragment extends BaseFragment {
    private final DataService dataService = DataService.getInstance();

    private People people;

    private ImageView img;
    private TextView tvName;
    private ImageButton btnExpand;
    private Runnable callback;

    public TransactionSetupPeopleSelectFragment() {
        super(R.layout.fragment_transaction_setup_people_select);
    }

    protected void init() {
        img = findViewById(R.id.img);
        tvName = findViewById(R.id.tv_name);

        btnExpand = findViewById(R.id.btn_search);
        btnExpand.setOnClickListener(v -> beginSearch());
        //getView().setOnClickListener(v -> beginSearch());
        initDataView();
    }

    private void initDataView() {
        if (people != null) {
            tvName.setText(people.getPseudonym());
        }
    }

    private void beginSearch() {
        runAsync(() -> {
            List<People> data = dataService.getAllPeoples();
            post(() -> {
                SearchPeopleDialog searchPeopleDialog = new SearchPeopleDialog(data);
                searchPeopleDialog.setCallback((result) -> {
                    people = result;
                    initDataView();
                    if (callback != null) {
                        callback.run();
                    }
                });
                searchPeopleDialog.show();
                //Can't find onCreated method in Dialog class
                postDelayed(searchPeopleDialog::showKeyboard, 100);
            });
        });
    }
    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public People getSelectedPeople() {
        return people;
    }
    public void setSelectedPeople(People people) {
        this.people = people;
    }
}