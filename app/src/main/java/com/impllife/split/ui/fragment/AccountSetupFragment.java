package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.service.DataService;

public class AccountSetupFragment extends NavFragment {
    private Button btnOk;
    private EditText etName;
    private EditText etAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_setup, container, false);
        setNavTitle("Account Setup");

        init(view);

        btnOk.setOnClickListener(v -> {
            Account account = new Account();
            account.setAmount(Double.parseDouble(String.valueOf(etAmount.getText())));
            account.setName(String.valueOf(etName.getText()));
            runAsync(() -> {
                DataService.getInstance().insert(account);
                view.post(() -> navController.navigateUp());
            });
        });
        return view;
    }

    private void init(View view) {
        btnOk = view.findViewById(R.id.btn_ok);
        etName = view.findViewById(R.id.et_name);
        etAmount = view.findViewById(R.id.et_amount);
    }
}