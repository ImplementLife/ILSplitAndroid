package com.impllife.split.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.impllife.split.R;
import com.impllife.split.ui.MainActivity;

import java.util.function.Consumer;

public class MainMenuFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        MainActivity.getInstance().hideHead();

        view.findViewById(R.id.btn_contacts).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.fragment_contacts);
        });
        view.findViewById(R.id.btn_transactions).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.fragment_transactions_list);
        });
        view.findViewById(R.id.btn_new_transaction).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.fragment_transaction_new);
        });
        view.findViewById(R.id.btn_test).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.fragment_test);
        });
        return view;
    }

    private static class Button {
        private int col;
        private int row;
        private String name;
        private Consumer<View> action;

    }
}