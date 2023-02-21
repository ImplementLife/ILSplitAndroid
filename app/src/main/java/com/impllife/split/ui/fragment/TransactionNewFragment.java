package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.service.DataService;

import java.util.Calendar;

public class TransactionNewFragment extends NavFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_new, container, false);
        setNavTitle("New transaction");

        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendar);
        Calendar calendar = Calendar.getInstance();
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth/*, 0, 0, 0*/);
        });

        view.findViewById(R.id.btn_create).setOnClickListener(v -> {
            Thread thread = new Thread(() -> {
                Transaction transaction = new Transaction();
                transaction.setSum(((EditText) view.findViewById(R.id.field_sum)).getText().toString());
                transaction.setDateCreate(calendar.getTime());
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