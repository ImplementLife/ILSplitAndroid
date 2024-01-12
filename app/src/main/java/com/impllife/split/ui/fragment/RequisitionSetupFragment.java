package com.impllife.split.ui.fragment;

import com.impllife.split.R;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;
import com.impllife.split.ui.custom.component.BaseView;
import com.impllife.split.ui.custom.component.CustomRecyclerView;
import com.impllife.split.ui.custom.component.NavFragment;

import static com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter.ModelViewData;

public class RequisitionSetupFragment extends NavFragment {
    public RequisitionSetupFragment() {
        super(R.layout.fragment_requisition_setup, "New Split");
    }

    @Override
    protected void init() {
        CustomRecyclerView rw = findViewById(R.id.list_item);
        AltRecyclerViewListAdapter adapter = new AltRecyclerViewListAdapter();
        rw.setAdapter(adapter);
        findViewById(R.id.btn_add).setOnClickListener(v -> {
            class Contact extends ModelViewData<String> {
                public Contact() {
                    super(R.layout.view_people_list_item, "Element");
                }

                @Override
                public void bindData(BaseView view) {
                    view.setTextViewById(R.id.tv_name, data);
                }
            }
            adapter.add(new Contact());
        });
    }
}