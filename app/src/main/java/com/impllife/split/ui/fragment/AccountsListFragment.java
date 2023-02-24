package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.impllife.split.R;

public class AccountsListFragment extends NavFragment {
    private LinearLayout list;
    private View btnNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts_list, container, false);
        setNavTitle("Accounts");

        init(view);
        return view;
    }

    private void init(View view) {
        list = view.findViewById(R.id.list);
        btnNew = view.findViewById(R.id.btn_new);

        btnNew.setOnClickListener(v -> {
            navController.navigate(R.id.fragment_account_setup);
        });
    }
}