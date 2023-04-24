package com.impllife.split.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.MainActivity;

import java.util.List;

public class AccountsListFragment extends NavFragment {
    private LinearLayout list;
    private View btnNew;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts_list, container, false);
        setNavTitle("Accounts");

        init(view);

        runAsync(() -> {
            List<Account> all = DataService.getInstance().getAllAccounts();
            view.post(() -> {
                for (Account account : all) {
                    TextView tv = new TextView(MainActivity.getInstance());
                    tv.setText(account.getName() + " : " + account.getAmount());
                    list.addView(tv);
                }
            });
        });

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