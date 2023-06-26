package com.impllife.split.ui.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.ui.custom.BtnRadioGroup;
import com.impllife.split.ui.custom.component.BaseView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.impllife.split.ui.custom.BtnRadioGroup.RadioBtn;

public class BtnDate extends BaseView implements RadioBtn {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
    private Date date;
    private TextView tvDate;
    private TextView tvName;

    private BtnRadioGroup group;

    public BtnDate(LayoutInflater inflater, ViewGroup rootForThis) {
        super(inflater, R.layout.view_btn_date, rootForThis);
        init();
    }

    private void init() {
        tvDate = findViewById(R.id.tv_date);
        tvName = findViewById(R.id.tv_name);
    }

    @Override
    public void select() {
        root.setBackgroundResource(R.drawable.sh_round_rectangle_focus);
        if (group != null) group.select(this);
    }

    @Override
    public void unselect() {
        root.setBackgroundResource(R.drawable.sh_round_rectangle);
    }

    @Override
    public void setGroup(BtnRadioGroup group) {
        this.group = group;
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
