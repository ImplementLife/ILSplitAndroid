package com.impllife.split.ui.view;

import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Budget;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;
import com.impllife.split.ui.custom.component.BaseView;

import static com.impllife.split.data.constant.Constant.ENTITY_ID;
import static com.impllife.split.service.util.Util.bundle;

public class BudgetListItem extends AltRecyclerViewListAdapter.Data<Budget> {
    private Budget data;

    public BudgetListItem(Budget data) {
        super(R.layout.view_budget_list_item);
        this.data = data;
    }

    @Override
    public void bindData(BaseView view) {
        view.setOnClickListener(v -> {
            MainActivity.getInstance().navController.navigate(R.id.fragment_budget_setup, bundle(ENTITY_ID, data.getId()));
        });
        view.setTextViewById(R.id.tv_name, data.getName());
        view.setTextViewById(R.id.tv_sum, data.getSumForPeriod().toPlainString());
        view.setTextViewById(R.id.tv_period, data.getPeriod().name());
    }

    @Override
    public Budget getData() {
        return data;
    }
}
