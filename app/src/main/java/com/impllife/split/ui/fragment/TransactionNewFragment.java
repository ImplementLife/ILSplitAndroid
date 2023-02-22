package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import androidx.annotation.Nullable;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.service.DataService;

import java.util.Calendar;
import java.util.Date;

public class TransactionNewFragment extends NavFragment {
    private Date dateCreate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_new, container, false);
        setNavTitle("New transaction");

        long dateCreateAsLong = 0;
        if (getArguments() != null) {
            dateCreateAsLong = getArguments().getLong("date");
        }
        if (dateCreateAsLong != 0) {
            this.dateCreate = new Date(dateCreateAsLong);
        } else {
            this.dateCreate = new Date();
        }

        view.findViewById(R.id.btn_date_select).setOnClickListener(v -> {
            navController.navigate(R.id.action_fragment_transaction_new_to_fragment_date_select);
        });

        view.findViewById(R.id.btn_create).setOnClickListener(v -> {
            Thread thread = new Thread(() -> {
                Transaction transaction = new Transaction();
                transaction.setSum(((EditText) view.findViewById(R.id.field_sum)).getText().toString());
                transaction.setDateCreate(dateCreate);
                transaction.setDescription(((EditText) view.findViewById(R.id.et_dscr)).getText().toString());
                DataService.getInstance().insert(transaction);
                view.post(() -> {
                    navController.navigateUp();
                });

            });
            thread.setDaemon(true);
            thread.start();

        });

        return view;
    }
}