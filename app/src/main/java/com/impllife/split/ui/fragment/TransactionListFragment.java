package com.impllife.split.ui.fragment;

import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Budget;
import com.impllife.split.data.jpa.entity.Transaction;
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
import java.util.stream.Collectors;

import static com.impllife.split.data.constant.Constant.ENTITY_ID;
import static com.impllife.split.data.jpa.entity.type.BudgetPeriod.*;
import static com.impllife.split.service.util.Util.*;

public class TransactionListFragment extends NavFragment {
    private final BudgetDao budgetDao = DataService.getInstance().getDb().getBudgetDao();
    private final DataService dataService = DataService.getInstance();
    private RecyclerView listItems;
    private AltRecyclerViewListAdapter adapter;

    public TransactionListFragment() {
        super(R.layout.fragment_transaction_list, "Transactions");
    }

    @Override
    protected void init() {
        listItems = findViewById(R.id.list_items);
        adapter = new AltRecyclerViewListAdapter();
        listItems.setAdapter(adapter);
        findViewById(R.id.btn_new).setOnClickListener(v -> navController.navigate(R.id.fragment_transaction_setup));

        runAsync(() -> {
            List<Transaction> allTransactions = dataService.getAllTransactions().stream().filter(e -> !e.getSum().equals("0.00")).collect(Collectors.toList());
            List<Budget> budgets = budgetDao.getAllByShowInTrn(true);
            runAsync(() -> {
                newListView(allTransactions, budgets);
            });
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

    public interface DataList {
        Date getDay();
    }

    public static class ListItemData extends AltRecyclerViewListAdapter.Data implements DataList {
        private final Transaction transaction;

        public ListItemData(Transaction transaction) {
            super(R.layout.view_simple_text);
            this.transaction = transaction;
        }

        @Override
        public Date getDay() {
            return transaction.getDateCreate();
        }

        @Override
        public void bindData(BaseView view) {
            view.setTextViewById(R.id.tv_data, transaction.getSum());
        }

        @Override
        public Object getData() {
            return null;
        }
    }

    public static class ListBudgetData extends AltRecyclerViewListAdapter.Data implements Comparable<ListBudgetData>, DataList {
        private final Budget budget;
        private final DateRange dateRange;
        private BigDecimal sumTotal = BigDecimal.ZERO;

        public ListBudgetData(Budget budget, DateRange dateRange) {
            super(R.layout.view_simple_text);
            this.budget = budget;
            this.dateRange = dateRange;
        }

        public void addSumTotal(BigDecimal sum) {
            this.sumTotal = this.sumTotal.add(sum);
        }

        public boolean inRange(Date date) {
            return dateRange.inRange(date);
        }
        public boolean isBefore(Date date) {
            return dateRange.isBefore(date);
        }
        public boolean isAfter(Date date) {
            return dateRange.isAfter(date);
        }

        @Override
        public Date getDay() {
            return dateRange.getEndDate();
        }


        @Override
        public void bindData(BaseView view) {
            view.setTextViewById(R.id.tv_data, budget.getPeriod().toString() + " " + sumTotal.toPlainString());
        }

        @Override
        public Object getData() {
            return null;
        }


        @Override
        public int compareTo(ListBudgetData o) {
            int otherKey = o.budget.getPeriod().getKey();
            int thisKey = budget.getPeriod().getKey();
            return Integer.compare(otherKey, thisKey);
        }
    }

    private void newListView(List<Transaction> sortedTransactions, List<Budget> budgets) {
        List<AltRecyclerViewListAdapter.Data> listViewData = new LinkedList<>();
        sortedTransactions.forEach(e -> listViewData.add(new ListItemData(e)));

        List<AltRecyclerViewListAdapter.Data> budgetsViewList = new LinkedList<>();

        for (Budget budget : budgets) {
            if (budget.isPeriod(DAY)) {
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
            if (budget.isPeriod(WEEK)) {
                int year = DateUtil.getCurrentYear();
                int week = DateUtil.getCurrentWeek();
                ListBudgetData itemBudget = new ListBudgetData(budget, DateUtil.getWeekDateRange(year, week));
                for (Transaction trn : sortedTransactions) {
                    if (!itemBudget.inRange(trn.getDateCreate())) {
                        budgetsViewList.add(itemBudget);
                        do {
                            week--;
                            if (week <= 0) {
                                year--;
                                week = DateUtil.getMaxWeekOfYear(year);
                            }
                            itemBudget = new ListBudgetData(budget, DateUtil.getWeekDateRange(year, week));
                        } while (!itemBudget.inRange(trn.getDateCreate()));
                    }
                    itemBudget.addSumTotal(new BigDecimal(trn.getSum()));
                }
            }
            if (budget.isPeriod(MONTH)) {
                int year = DateUtil.getCurrentYear();
                int month = DateUtil.getCurrentMonth();
            }
            if (budget.isPeriod(QUARTER)) {
                int year = DateUtil.getCurrentYear();
            }
            if (budget.isPeriod(YEAR)) {
                int year = DateUtil.getCurrentYear();
            }
        }
        listViewData.addAll(budgetsViewList);
        listViewData.sort((e1, e2) -> {
            Date date1 = ((DataList) e1).getDay();
            Date date2 = ((DataList) e2).getDay();
            int compare = date2.compareTo(date1);
            if (DateUtil.isSameDay(date1, date2)) {
                compare = 0;
            }
            if (compare == 0) {
                boolean e1isListBudgetData = e1 instanceof ListBudgetData;
                boolean e2isListBudgetData = e2 instanceof ListBudgetData;
                if (e1isListBudgetData && e2isListBudgetData) {
                    compare = ((ListBudgetData) e1).compareTo((ListBudgetData) e2);
                }
                if (e1isListBudgetData != e2isListBudgetData) {
                    compare = e1isListBudgetData ? 1 : -1;
                }
            }

            return compare;
        });

        updateView(() -> {
            adapter.replaceAll(listViewData);
            listItems.scrollToPosition(0);
        });
    }
}