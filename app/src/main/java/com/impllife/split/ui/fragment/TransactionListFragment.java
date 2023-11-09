package com.impllife.split.ui.fragment;

import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Budget;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.entity.type.BudgetPeriod;
import com.impllife.split.data.jpa.provide.BudgetDao;
import com.impllife.split.service.DataService;
import com.impllife.split.service.util.date.DateRange;
import com.impllife.split.service.util.date.DateUtil;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;
import com.impllife.split.ui.custom.component.BaseView;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.view.TransactionListItem;
import com.impllife.split.ui.view.TransactionListItemDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.impllife.split.data.constant.Constant.ENTITY_ID;
import static com.impllife.split.service.util.Util.*;

public class TransactionListFragment extends NavFragment {
    private final BudgetDao budgetDao = DataService.getInstance().getDb().getBudgetDao();
    private DataService dataService = DataService.getInstance();
    private RecyclerView listItems;
    private AltRecyclerViewListAdapter adapter;

    public TransactionListFragment() {
        super(R.layout.fragment_transaction_list, "Transactions");
    }

    @Override
    protected void init() {
        listItems = findViewById(R.id.list_items);
        findViewById(R.id.btn_new).setOnClickListener(v -> navController.navigate(R.id.fragment_transaction_setup));

        runAsync(() -> {
            List<Transaction> allTransactions = dataService.getAllTransactions();
            List<Budget> budgets = budgetDao.getAllByShowInTrn(true);
            runAsync(() -> {
                newListView(allTransactions, budgets);
            });
//            post(() -> updateView(createListView(allTransactions)));
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

        for (Transaction transaction : sortedTransactions) {
            Date date = transaction.getDateCreate();
            if (!equalsDateByDMY(currentProcessingDay, date)) {
                TransactionListItemDate viewDate = new TransactionListItemDate(inflater, listItems, date);
                result.add(viewDate);

                currentViewDate = viewDate;
                currentProcessingDay = date;
                currentSumTotal = 0D;
            }
            currentSumTotal += Double.parseDouble(isBlank(transaction.getSum()) ? "0" : transaction.getSum());
            currentViewDate.setData(String.valueOf(currentSumTotal), budgetCommon);

            TransactionListItem transactionListItem = new TransactionListItem(inflater, listItems, transaction);
            transactionListItem.setOnClick(v -> navController.navigate(R.id.fragment_transaction_setup, bundle(ENTITY_ID, transaction.getId())));
            result.add(transactionListItem);
        }

        return result;
    }

    private interface ListData {

    }

    private static class ListItemData implements ListData {
        private Transaction transaction;
    }
    private static class ListBudgetData implements ListData {
        private final Budget budget;
        private final DateRange dateRange;
        private BigDecimal sumTotal = BigDecimal.ZERO;

        public ListBudgetData(Budget budget, DateRange dateRange) {
            this.budget = budget;
            this.dateRange = dateRange;
        }

        public void addSumTotal(BigDecimal sum) {
            this.sumTotal = this.sumTotal.add(sum);
        }

        public Budget getBudget() {
            return budget;
        }

        public BigDecimal getSumTotal() {
            return sumTotal;
        }

        public DateRange getDateRange() {
            return dateRange;
        }

        public boolean inRange(Date date) {
            return dateRange.inRange(date);
        }
    }

    private void newListView(List<Transaction> sortedTransactions, List<Budget> budgets) {
        List<AltRecyclerViewListAdapter.Data> listViewData = new LinkedList<>();

        List<ListData> budgetsViewList = new LinkedList<>();

        for (Budget budget : budgets) {
            if (budget.getPeriod() == BudgetPeriod.DAY) {
                Date date = new Date();
                ListBudgetData itemBudget = new ListBudgetData(budget, DateUtil.getDayDateRange(date));
                for (Transaction trn : sortedTransactions) {
                    if (!itemBudget.inRange(trn.getDateCreate())) {
                        budgetsViewList.add(itemBudget);
                        date = DateUtil.getPreviousDay(date);
                        itemBudget = new ListBudgetData(budget, DateUtil.getDayDateRange(date));
                    }
                    itemBudget.addSumTotal(new BigDecimal(trn.getSum()));
                }
            }
            if (budget.getPeriod() == BudgetPeriod.WEEK) {
                int year = DateUtil.getCurrentYear();
                int week = DateUtil.getCurrentWeek();
                ListBudgetData itemBudget = new ListBudgetData(budget, DateUtil.getWeekDateRange(year, week));
                for (Transaction trn : sortedTransactions) {
                    if (!itemBudget.inRange(trn.getDateCreate())) {
                        budgetsViewList.add(itemBudget);

                        week--;
                        if (week <= 0) {
                            year--;
                            week = DateUtil.getMaxWeekOfYear(year);
                        }
                        itemBudget = new ListBudgetData(budget, DateUtil.getWeekDateRange(year, week));
                    }
                    itemBudget.addSumTotal(new BigDecimal(trn.getSum()));
                }
            }
        }

        updateView(() -> {
            adapter.replaceAll(listViewData);
            listItems.scrollToPosition(0);
        });
    }


    private void updateView(List<BaseView> list) {
        listItems.removeAllViews();
        list.forEach(e -> listItems.addView(e.getRoot()));
    }

}