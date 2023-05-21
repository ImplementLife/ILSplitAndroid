package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import com.impllife.split.R;
import com.impllife.split.ui.custom.component.NavFragment;

import java.util.Calendar;

public class DateSelectFragment extends NavFragment {
    public static final String RESULT_KEY = "date_select_result";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_select, container, false);
        setNavTitle("Choose date");

        Calendar calendar = Calendar.getInstance();
        CalendarView calendarView = view.findViewById(R.id.calendar);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth, 0, 0, 0);
        });

        view.findViewById(R.id.btn_ok).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("date", calendar.getTimeInMillis());
            getParentFragmentManager().setFragmentResult(RESULT_KEY, bundle);
//            navController.navigate(R.id.action_fragment_date_select_to_fragment_transaction_new, bundle);
            navController.navigateUp();
        });
        return view;
    }
}