package com.impllife.split.ui.custom.adapter;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class TabPagerAdapter extends FragmentStateAdapter {
    private final List<TabInfo> tabInfoList;

    public TabPagerAdapter(Fragment fragmentActivity, List<TabInfo> tabInfoList) {
        super(fragmentActivity);
        this.tabInfoList = tabInfoList;
    }

    @Override
    public int getItemCount() {
        return tabInfoList.size();
    }

    @Override
    public Fragment createFragment(int position) {
        return tabInfoList.get(position).fragment;
    }


    public static class TabInfo {
        private final String name;
        private final Fragment fragment;

        public TabInfo(String name, Fragment fragment) {
            this.name = name;
            this.fragment = fragment;
        }

        public String getName() {
            return name;
        }

        public Fragment getFragment() {
            return fragment;
        }
    }
}
