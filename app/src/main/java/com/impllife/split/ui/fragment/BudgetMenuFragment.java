package com.impllife.split.ui.fragment;

import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.impllife.split.R;
import com.impllife.split.ui.custom.adapter.TabPagerAdapter;
import com.impllife.split.ui.custom.component.NavFragment;

import java.util.ArrayList;
import java.util.List;

public class BudgetMenuFragment extends NavFragment {

    public BudgetMenuFragment() {
        super(R.layout.fragment_budget_menu, "Budget");
    }


    @Override
    protected void init() {
        ViewPager2 pager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tab);

        List<TabPagerAdapter.TabInfo> tabInfoList = new ArrayList<>();
        tabInfoList.add(new TabPagerAdapter.TabInfo("Accounts", new AccountListFragment()));
        tabInfoList.add(new TabPagerAdapter.TabInfo("Budget", new BudgetListFragment()));

        pager.setAdapter(new TabPagerAdapter(this, tabInfoList));

        new TabLayoutMediator(tabLayout, pager, (tab, position) -> {
            tab.setText(tabInfoList.get(position).getName());
        }).attach();
    }
}