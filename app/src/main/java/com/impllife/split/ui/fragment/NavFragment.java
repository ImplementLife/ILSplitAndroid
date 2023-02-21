package com.impllife.split.ui.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.impllife.split.ui.MainActivity;

public class NavFragment extends BaseFragment {
    protected NavController navController;

    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    protected void setNavTitle(String name) {
        MainActivity.getInstance().showHead();
        MainActivity.getInstance().hideInfoBtn();
        MainActivity.getInstance().setHeadTitle(name);
    }

    protected void setNavTitle(String name, Runnable btnInfoAction) {
        MainActivity.getInstance().showHead();
        MainActivity.getInstance().setHeadTitle(name);
        MainActivity.getInstance().showInfoBtn();
        MainActivity.getInstance().setInfoBtnAction(btnInfoAction);
    }
}
