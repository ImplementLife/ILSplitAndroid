package com.impllife.split.ui.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.impllife.split.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionListItemDate extends BaseView {
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private TextView tvDate;

    public TransactionListItemDate(LayoutInflater inflater, ViewGroup rootForThis, Date date) {
        super(inflater, R.layout.view_transactoin_list_item_date, rootForThis);
        init();
        setData(date);
    }

    private void init() {
        tvDate = findViewById(R.id.tv_date);
    }

    public void setData(Date date) {
        tvDate.setText(dateFormat.format(date));
    }
}
