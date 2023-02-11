package com.impllife.split.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.impllife.split.R;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class ContactsFragment extends NavFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        setNavTitle("Contacts");

        Map<Integer, TabInfo> tabInfoMap = new TreeMap<>();
        tabInfoMap.put(0, new TabInfo("Peoples", ContactsPeoplesFragment.newInstance()));
        tabInfoMap.put(1, new TabInfo("Groups", ContactsGroupsFragment.newInstance()));

        ViewPager2 pager = view.findViewById(R.id.pager);
        FragmentStateAdapter pageAdapter = new FragmentStateAdapter(this) {
            public int getItemCount() {
                return tabInfoMap.size();
            }
            public Fragment createFragment(int position) {
                return Objects.requireNonNull(tabInfoMap.get(position)).fragment;
            }
        };
        pager.setAdapter(pageAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, pager, (tab, position) -> {
            tab.setText(Objects.requireNonNull(tabInfoMap.get(position)).name);
        });
        tabLayoutMediator.attach();

        return view;
    }

    private class TabInfo {
        String name;
        Fragment fragment;

        public TabInfo(String name, Fragment fragment) {
            this.name = name;
            this.fragment = fragment;
        }
    }
}