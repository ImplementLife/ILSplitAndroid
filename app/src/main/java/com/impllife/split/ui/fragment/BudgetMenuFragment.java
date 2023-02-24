package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.impllife.split.R;

public class BudgetMenuFragment extends NavFragment {
    private LinearLayout list;
    private View btnAccounts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget_menu, container, false);
        init(view);



        return view;
    }

    private void init(View view) {
        list = view.findViewById(R.id.list);
        btnAccounts = view.findViewById(R.id.btn_accounts);


        btnAccounts.setOnClickListener(v -> {

        });
    }
}