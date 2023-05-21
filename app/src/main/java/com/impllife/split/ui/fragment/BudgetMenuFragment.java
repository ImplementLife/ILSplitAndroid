package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.impllife.split.R;
import com.impllife.split.ui.custom.component.NavFragment;

public class BudgetMenuFragment extends NavFragment {
    private LinearLayout list;
    private View btnAccounts;
    private View btnFamilyBudget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget_menu, container, false);
        setNavTitle("Budget");

        init(view);

        return view;
    }

    private void init(View view) {
        list = view.findViewById(R.id.list);
        btnAccounts = view.findViewById(R.id.btn_accounts);
        btnFamilyBudget = view.findViewById(R.id.btn_family_budget);


        btnAccounts.setOnClickListener(v -> {
            navController.navigate(R.id.fragment_accounts_list);
        });

        btnFamilyBudget.setOnClickListener(v -> {

        });
    }
}