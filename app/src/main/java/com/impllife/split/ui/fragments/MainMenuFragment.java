package com.impllife.split.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.impllife.split.R;
import com.impllife.split.ui.MainActivity;

public class MainMenuFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        MainActivity.getInstance().hideHead();

        view.findViewById(R.id.btn_go).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.contactsFragment);
        });
        return view;
    }

}