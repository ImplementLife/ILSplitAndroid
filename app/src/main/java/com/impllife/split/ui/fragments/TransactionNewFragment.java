package com.impllife.split.ui.fragments;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.service.ComService;
import com.impllife.split.ui.MainActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionNewFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_new, container, false);
        MainActivity.getInstance().showHead();
        MainActivity.getInstance().setHeadTitle("New transaction");
        NavController navController = NavHostFragment.findNavController(this);

        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendar);
        Calendar calendar = Calendar.getInstance();
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth, 0, 0, 0);
        });

        view.findViewById(R.id.btn_create).setOnClickListener(v -> {
            Thread thread = new Thread(() -> {
                Transaction transaction = new Transaction();
                transaction.setSum(((EditText) view.findViewById(R.id.field_sum)).getText().toString());
                transaction.setDateCreate(calendar.getTimeInMillis());
                ComService.getInstance().insert(transaction);
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