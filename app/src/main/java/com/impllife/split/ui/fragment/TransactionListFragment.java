package com.impllife.split.ui.fragment;

import android.widget.LinearLayout;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Budget;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.entity.type.BudgetPeriod;
import com.impllife.split.data.jpa.provide.BudgetDao;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.custom.component.BaseView;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.view.TransactionListItem;
import com.impllife.split.ui.view.TransactionListItemDate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.impllife.split.data.constant.Constant.ENTITY_ID;
import static com.impllife.split.service.util.Util.*;

public class TransactionListFragment extends NavFragment {
    private final BudgetDao budgetDao = DataService.getInstance().getDb().getBudgetDao();
    private DataService dataService = DataService.getInstance();
    private LinearLayout listItems;
    private List<Budget> budgets;

    public TransactionListFragment() {
        super(R.layout.fragment_transaction_list, "Transactions");
    }

    @Override
    protected void init() {
        listItems = findViewById(R.id.list_items);
        findViewById(R.id.btn_new).setOnClickListener(v -> navController.navigate(R.id.fragment_transaction_setup));

        runAsync(() -> {
            List<Transaction> allTransactions = dataService.getAllTransactions();
            budgets = budgetDao.getAllByShowInTrn(true);
            post(() -> updateView(createListView(allTransactions)));
        });
    }

    private List<BaseView> createListView(List<Transaction> sortedTransactions) {
        List<BaseView> result = new ArrayList<>();
        if (sortedTransactions.size() == 0) return result;

        Date startDate = sortedTransactions.get(0).getDateCreate();
        Date currentProcessingDay = startDate;

        TransactionListItemDate currentViewDate = new TransactionListItemDate(inflater, listItems, startDate);
        double currentSumTotal = 0;

        result.add(currentViewDate);
        BigDecimal budgetCommon = BigDecimal.ZERO;
        for (Budget budget : budgets) {
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

        for (Transaction transaction : sortedTransactions) {
            Date date = transaction.getDateCreate();
            if (!equalsDateByDMY(currentProcessingDay, date)) {
                TransactionListItemDate viewDate = new TransactionListItemDate(inflater, listItems, date);
                result.add(viewDate);

                currentViewDate = viewDate;
                currentProcessingDay = date;
                currentSumTotal = 0D;
            }
            currentSumTotal += Double.parseDouble(isBlank(transaction.getSum())?"0":transaction.getSum());
            currentViewDate.setData(String.valueOf(currentSumTotal), budgetCommon);

            TransactionListItem transactionListItem = new TransactionListItem(inflater, listItems, transaction);
            transactionListItem.setOnClick(v -> navController.navigate(R.id.fragment_transaction_setup, bundle(ENTITY_ID, transaction.getId())));
            result.add(transactionListItem);
        }

        return result;
    }

    private void updateView(List<BaseView> list) {
        listItems.removeAllViews();
        list.forEach(e -> listItems.addView(e.getRoot()));
    }

}