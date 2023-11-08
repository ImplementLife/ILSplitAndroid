package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.impllife.split.R;
import com.impllife.split.ui.custom.adapter.TabPagerAdapter;
import com.impllife.split.ui.custom.component.NavFragment;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends NavFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        setNavTitle("Contacts");

        ViewPager2 pager = view.findViewById(R.id.pager);
        TabLayout tabLayout = view.findViewById(R.id.tab);

        List<TabPagerAdapter.TabInfo> tabInfoList = new ArrayList<>();
        tabInfoList.add(new TabPagerAdapter.TabInfo("Peoples", new ContactPeoplesFragment()));
        tabInfoList.add(new TabPagerAdapter.TabInfo("Groups", new ContactGroupListFragment()));

        pager.setAdapter(new TabPagerAdapter(this, tabInfoList));

        new TabLayoutMediator(tabLayout, pager, (tab, position) -> {
            tab.setText(tabInfoList.get(position).getName());
        }).attach();

        return view;
    }
}