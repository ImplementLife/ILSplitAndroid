package com.impllife.split.ui.fragment;

import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.custom.adapter.UniversalRVListAdapter;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.view.AccountListItem;

import java.util.List;

public class AccountListFragment extends NavFragment {
    private final DataService dataService = DataService.getInstance();
    private UniversalRVListAdapter adapter;

    public AccountListFragment() {
        super(R.layout.fragment_account_list, false);
    }

    @Override
    protected void init() {
        RecyclerView list = findViewById(R.id.list);
        adapter = new UniversalRVListAdapter();
        list.setAdapter(adapter);
        findViewById(R.id.btn_new).setOnClickListener(v -> navController.navigate(R.id.fragment_account_setup));

        runAsync(() -> {
            List<Account> allAccounts = dataService.getAllAccounts();
            updateView(() -> {
                allAccounts.forEach(account -> {
                    adapter.add(new AccountListItem(navController, account));
                });
            });
        });
    }
}