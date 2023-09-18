package com.impllife.split.ui.fragment;

import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Budget;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.view.BudgetListItem;

import java.util.ArrayList;
import java.util.List;

public class BudgetListFragment extends NavFragment {
    private AltRecyclerViewListAdapter adapter;
    private RecyclerView recyclerView;

    public BudgetListFragment() {
        super(R.layout.fragment_budget_list, "Budgets");
    }

    @Override
    protected void init() {
        recyclerView = findViewById(R.id.list);
        adapter = new AltRecyclerViewListAdapter();
        recyclerView.setAdapter(adapter);

        updateList();
    }
    private void updateList() {
        List<AltRecyclerViewListAdapter.Data> collect = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Budget budget = new Budget();
            budget.setName("Name " + (i + 1));
            collect.add(new BudgetListItem(budget));
        }
        updateView(() -> {
            adapter.replaceAll(collect);
            recyclerView.scrollToPosition(0);
        });
    }
}