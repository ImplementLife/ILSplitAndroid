package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import com.impllife.split.R;

public class DateSelectFragment extends NavFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_select, container, false);
        setNavTitle("Choose date");
        CalendarView calendar = view.findViewById(R.id.calendar);
        view.findViewById(R.id.btn_ok).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("date", calendar.getDate());
            navController.navigate(R.id.action_fragment_date_select_to_fragment_transaction_new, bundle);
        });
        return view;
    }
}