package com.impllife.split.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.custom.adapter.RecyclerViewListAdapter;
import com.impllife.split.ui.custom.adapter.RecyclerViewListAdapter.ViewData;
import com.impllife.split.ui.custom.component.CustomRecyclerView;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPeopleDialog extends Dialog {
    private EditText fieldQuery;

    private CustomRecyclerView list;
    private RecyclerViewListAdapter<People> adapter;
    private Runnable callback;

    private List<People> dataForSearch;
    private People result;

    public SearchPeopleDialog(List<People> dataForSearch) {
        super(MainActivity.getInstance());
        this.dataForSearch = dataForSearch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_search_people);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        fieldQuery = findViewById(R.id.field_query);
        fieldQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().toLowerCase();
                Comparator<ViewData<People>> comparator = Comparator
                    .comparing((ViewData<People> p) -> !p.getData().getPseudonym().toLowerCase().startsWith(query))
                    .thenComparing((ViewData<People> p) -> p.getData().getPseudonym().toLowerCase());
                adapter.sort(comparator);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        list = findViewById(R.id.list);
        adapter = new RecyclerViewListAdapter<>((data, view) -> {
            view.setTextViewById(R.id.tv_name, data.getPseudonym());
            view.setOnClickListener(v -> {
                result = data;
                if (callback != null) {
                    callback.run();
                }
                dismiss();
            });
        });
        list.setAdapter(adapter);
        adapter.replaceAll(dataForSearch.stream().map(e -> new ViewData<>(R.layout.view_people_list_item, e)).collect(Collectors.toList()));
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }
    public People getResult() {
        return result;
    }

    public void showKeyboard() {
        MainActivity.getInstance().showKeyboard(fieldQuery);
    }
}
