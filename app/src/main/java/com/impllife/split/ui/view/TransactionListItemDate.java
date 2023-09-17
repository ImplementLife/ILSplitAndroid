package com.impllife.split.ui.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.service.util.Formatters;
import com.impllife.split.ui.custom.component.BaseView;
import com.impllife.split.ui.custom.component.StatusBar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class TransactionListItemDate extends BaseView {
    private static final BigDecimal budget = BigDecimal.valueOf(900);
    private TextView tvDate;
    private TextView tvSumTotal;
    private TextView tvPercent;
    private StatusBar statusBar;

    public TransactionListItemDate(LayoutInflater inflater, ViewGroup rootForThis, Date date) {
        super(inflater, R.layout.view_transactoin_list_item_date, rootForThis);
        init();
        setData(date);
    }

    private void init() {
        tvDate = findViewById(R.id.tv_date);
        tvSumTotal = findViewById(R.id.tv_sum_total);
        tvPercent = findViewById(R.id.tv_percent);
        statusBar = findViewById(R.id.status_bar);
    }

    public void setData(Date date) {
        tvDate.setText(Formatters.formatDDMMYYYY(date));
    }

    public void setData(String sumTotal) {
        tvSumTotal.setText(sumTotal);
        BigDecimal sum = new BigDecimal(sumTotal);
        if (sum.compareTo(BigDecimal.ZERO) < 0) sum = sum.negate();
        BigDecimal asPercent = sum.multiply(BigDecimal.valueOf(100)).divide(budget, RoundingMode.CEILING);
        statusBar.setProgress(asPercent.intValue());
        tvPercent.setText(asPercent.intValue() + "%");
    }
}
