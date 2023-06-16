package com.impllife.split.ui.dialog;

import android.os.Bundle;
import android.view.Window;
import android.widget.CalendarView;
import com.impllife.split.R;
import com.impllife.split.ui.custom.CustomDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.function.Consumer;

import static android.view.ViewGroup.LayoutParams;

public class CalendarDialog extends CustomDialog {
    private final Consumer<Date> onDateSelected;
    private final Calendar calendar = Calendar.getInstance();

    public CalendarDialog(Consumer<Date> onDateSelected) {
        Objects.requireNonNull(onDateSelected);
        this.onDateSelected = onDateSelected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_calendar);
        getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        CalendarView calendarView = findViewById(R.id.calendar);
        calendarView.setMaxDate(calendar.getTimeInMillis());
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth, 0, 0, 0);
        });

        findViewById(R.id.btn_ok).setOnClickListener(v -> {
            onDateSelected.accept(calendar.getTime());
            dismiss();
        });
        setOnCancelListener(v -> {
            calendarView.setDate(new Date().getTime());
            onDateSelected.accept(null);
        });
    }
}
