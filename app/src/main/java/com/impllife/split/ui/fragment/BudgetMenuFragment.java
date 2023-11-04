package com.impllife.split.ui.fragment;

import android.view.View;
import android.widget.LinearLayout;
import com.impllife.split.R;
import com.impllife.split.ui.custom.component.NavFragment;

public class BudgetMenuFragment extends NavFragment {
    private LinearLayout list;
    private View btnAccounts;
    private View btnFamilyBudget;

    public BudgetMenuFragment() {
        super(R.layout.fragment_budget_menu, "Budget");
    }


    @Override
    protected void init() {
        list = findViewById(R.id.list);
        btnAccounts = findViewById(R.id.btn_accounts);
        btnFamilyBudget = findViewById(R.id.btn_family_budget);


        btnAccounts.setOnClickListener(v -> {
            navController.navigate(R.id.fragment_account_list);
        });

        btnFamilyBudget.setOnClickListener(v -> {
            navController.navigate(R.id.fragment_budget_list);
        });
    }
}