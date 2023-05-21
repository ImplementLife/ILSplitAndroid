package com.impllife.split.ui.custom.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.impllife.split.ui.MainActivity;

public class NavFragment extends BaseFragment {
    protected NavController navController;
    private String name;

    public NavFragment() {}
    public NavFragment(int layout) {
        super(layout);
    }
    public NavFragment(int layout, String name) {
        super(layout);
        this.name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (name != null) {
            setNavTitle(name);
        } else {
            MainActivity.getInstance().hideHead();
        }
        return view;
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

    public void navigate(int resId) {
        navController.navigate(resId);
    }

    public void navigate(int resId, Bundle args) {
        navController.navigate(resId, args);
    }

    public boolean navigateUp() {
        return navController.navigateUp();
    }
}
