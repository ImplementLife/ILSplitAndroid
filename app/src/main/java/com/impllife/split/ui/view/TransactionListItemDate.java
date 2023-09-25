package com.impllife.split.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Budget;
import com.impllife.split.data.jpa.entity.type.BudgetPeriod;
import com.impllife.split.data.jpa.provide.BudgetDao;
import com.impllife.split.service.DataService;
import com.impllife.split.service.util.Formatters;
import com.impllife.split.ui.custom.component.BaseView;
import com.impllife.split.ui.custom.component.StatusBar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class TransactionListItemDate extends BaseView {
    private static final BudgetDao budgetDao = DataService.getInstance().getDb().getBudgetDao();
    private BigDecimal budgetCommon = BigDecimal.ZERO;
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
        runAsync(() -> {
            for (Budget budget : budgetDao.getAllByShowInTrn(true)) {
                BudgetPeriod period = budget.getPeriod();
                BigDecimal sumForPeriod = budget.getSumForPeriod();
                if (period == BudgetPeriod.WEEK) {
                    sumForPeriod = sumForPeriod.divide(BigDecimal.valueOf(7), RoundingMode.CEILING);
                } else if (period == BudgetPeriod.MONTH) {
                    sumForPeriod = sumForPeriod.divide(BigDecimal.valueOf(31), RoundingMode.CEILING);
                } else if (period == BudgetPeriod.QUARTER) {
                    sumForPeriod = sumForPeriod.divide(BigDecimal.valueOf(93), RoundingMode.CEILING);
                }
                budgetCommon = budgetCommon.add(sumForPeriod);
            }
            post(() -> {
                if (!BigDecimal.ZERO.equals(budgetCommon)) {
                    BigDecimal sum = new BigDecimal(sumTotal);
                    if (sum.compareTo(BigDecimal.ZERO) < 0) sum = sum.negate();
                    BigDecimal asPercent = sum.multiply(BigDecimal.valueOf(100)).divide(budgetCommon, RoundingMode.CEILING);
                    statusBar.setProgress(asPercent.intValue());
                    tvPercent.setText(asPercent.intValue() + "%");
                } else {
                    statusBar.setVisibility(View.GONE);
                    tvPercent.setVisibility(View.GONE);
                }
            });
        });
    }
}
