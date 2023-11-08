package com.impllife.split.ui.fragment;

import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Budget;
import com.impllife.split.data.jpa.provide.BudgetDao;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter.Data;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.view.BudgetListItem;

import java.util.ArrayList;
import java.util.List;

public class BudgetListFragment extends NavFragment {
    private static final BudgetDao budgetDao = DataService.getInstance().getDb().getBudgetDao();
    private AltRecyclerViewListAdapter adapter;
    private RecyclerView recyclerView;

    public BudgetListFragment() {
        super(R.layout.fragment_budget_list, false);
    }

    @Override
    protected void init() {
        findViewById(R.id.btn_new).setOnClickListener(v -> {
            navController.navigate(R.id.fragment_budget_setup);
        });
        recyclerView = findViewById(R.id.list);
        adapter = new AltRecyclerViewListAdapter();
        recyclerView.setAdapter(adapter);

        updateList();
    }

    private void updateList() {
        runAsync(() -> {
            List<Data> collect = new ArrayList<>();
            List<Budget> allBudget = budgetDao.getAll();
            for (Budget budget : allBudget) {
                collect.add(new BudgetListItem(budget));
            }
            updateView(() -> {
                adapter.replaceAll(collect);
                recyclerView.scrollToPosition(0);
            });
        });
    }
}