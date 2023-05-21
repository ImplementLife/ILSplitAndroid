package com.impllife.split.ui.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.ui.custom.component.BaseView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BtnDate extends BaseView {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
    private Date date;
    private TextView tvDate;
    private TextView tvName;

    public BtnDate(LayoutInflater inflater, ViewGroup rootForThis) {
        super(inflater, R.layout.view_btn_date, rootForThis);
        init();
    }

    private void init() {
        tvDate = findViewById(R.id.tv_date);
        tvName = findViewById(R.id.tv_name);
    }

    public void select() {
        root.setBackgroundColor(R.attr.colorSecondaryVariant);
    }

    public void unselect() {
        root.setBackgroundColor(R.attr.colorPrimaryVariant);
    }

    public void setName(String name) {
        tvName.setText(name);
    }

    public void setDate(Date date) {
        this.date = date;
        if (date != null) {
            this.tvDate.setText(dateFormat.format(date));
        } else {
            this.tvDate.setText("--.--");
        }
    }

    public Date getDate() {
        return date;
    }
}
