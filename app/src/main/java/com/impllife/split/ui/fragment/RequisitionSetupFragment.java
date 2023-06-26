package com.impllife.split.ui.fragment;

import com.impllife.split.R;
import com.impllife.split.ui.custom.adapter.RecyclerViewListAdapter;
import com.impllife.split.ui.custom.component.CustomRecyclerView;
import com.impllife.split.ui.custom.component.NavFragment;

import static com.impllife.split.ui.custom.adapter.RecyclerViewListAdapter.ViewData;

public class RequisitionSetupFragment extends NavFragment {
    public RequisitionSetupFragment() {
        super(R.layout.fragment_requisition_setup, "New Split");
    }

    @Override
    protected void init() {
        CustomRecyclerView rw = findViewById(R.id.list_item);
        RecyclerViewListAdapter<String> adapter = new RecyclerViewListAdapter<>((data, view) -> {
            view.setTextViewById(R.id.tv_name, data);
        });
        rw.setAdapter(adapter);
        findViewById(R.id.btn_add).setOnClickListener(v -> {
            adapter.add(new ViewData<>(R.layout.view_people_list_item,"Element"), 0);
        });
    }
}