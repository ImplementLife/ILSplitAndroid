package com.impllife.split.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.R;
import com.impllife.split.data.constant.DefaultUserIcon;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.data.jpa.entity.Budget;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.provide.BudgetDao;
import com.impllife.split.data.jpa.provide.TransactionDao;
import com.impllife.split.service.DataService;
import com.impllife.split.service.TransactionService;
import com.impllife.split.service.util.Formatters;
import com.impllife.split.service.util.date.DateRange;
import com.impllife.split.service.util.date.DateUtil;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;
import com.impllife.split.ui.custom.component.BaseView;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.custom.component.StatusBar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.impllife.split.data.constant.Constant.ENTITY_ID;
import static com.impllife.split.data.jpa.entity.type.BudgetPeriod.*;
import static com.impllife.split.service.util.Util.bundle;
import static com.impllife.split.service.util.Util.isBlank;

public class TransactionListFragment extends NavFragment {
    private final BudgetDao budgetDao = DataService.getInstance().getDb().getBudgetDao();
    private final TransactionDao transactionDao = new TransactionService();
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
            List<Transaction> allTransactions = transactionDao.getAllEager().stream()
                .filter(e -> !e.getSum().equals("0.00"))
                .collect(Collectors.toList());
            List<Budget> budgets = budgetDao.getAllByShowInTrn(true);
            runAsync(() -> {
                newListView(allTransactions, budgets);
            });
        });
    }

    public interface DataList {
        Date getDay();
    }

    public static class ListItemDataView extends AltRecyclerViewListAdapter.Data implements DataList {
        private final Transaction transaction;
        private final NavController navController;

        public ListItemDataView(Transaction transaction, NavController navController) {
            super(R.layout.view_transactoin_list_item);
            this.transaction = transaction;
            this.navController = navController;
        }

        @Override
        public Date getDay() {
            return transaction.getDateCreate();
        }

        @Override
        public void bindData(BaseView view) {
            view.setTextViewById(R.id.tv_sum, transaction.getSum());
            view.setOnClickListener(v -> navController.navigate(R.id.fragment_transaction_setup, bundle(ENTITY_ID, transaction.getId())));

            People fromPeople = transaction.getFromPeople();
            Account fromAccount = transaction.getFromAccount();

            People toPeople = transaction.getToPeople();
            Account toAccount = transaction.getToAccount();

            {
                ImageView image = view.findViewById(R.id.image);
                People peopleForIcon = null;
                if (fromPeople != null) peopleForIcon = fromPeople;
                if (toPeople != null) peopleForIcon = toPeople;

                if (peopleForIcon != null) {
                    String iconName = peopleForIcon.getIcon();
                    if (!isBlank(iconName)) {
                        DefaultUserIcon.parse(iconName)
                            .ifPresent(userIcon -> image.setImageResource(userIcon.getResId()));
                    }
                } else {
                    if (new BigDecimal(transaction.getSum()).compareTo(BigDecimal.ZERO) > 0) {
                        image.setImageResource(R.drawable.ic_svg_transaction);
                    } else {
                        image.setImageResource(R.drawable.ic_svg_transaction_alt);
                    }
                }

                if (isBlank(transaction.getDescription())) {
                    if (peopleForIcon != null) {
                        view.setTextViewById(R.id.tv_dscr, peopleForIcon.getPseudonym());
                    } else {
                        view.setTextViewById(R.id.tv_dscr, "");
                    }
                } else {
                    view.setTextViewById(R.id.tv_dscr, transaction.getDescription());
                }
            }
        }

        @Override
        public Object getData() {
            return null;
        }
    }

    public static class ListBudgetDataView extends AltRecyclerViewListAdapter.Data implements Comparable<ListBudgetDataView>, DataList {
        private final Budget budget;
        private final DateRange dateRange;
        private BigDecimal sumTotal = BigDecimal.ZERO;

        public ListBudgetDataView(Budget budget, DateRange dateRange) {
            super(R.layout.view_transactoin_list_item_budget);
            this.budget = budget;
            this.dateRange = dateRange;
        }

        public ListBudgetDataView(Date date) {
            super(R.layout.view_transactoin_list_item_date);
            dateRange = null;
            budget = null;
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
            TextView tvDate = view.findViewById(R.id.tv_date);
            TextView tvSumTotal = view.findViewById(R.id.tv_sum_total);
            TextView tvPercent = view.findViewById(R.id.tv_percent);
            StatusBar statusBar = view.findViewById(R.id.status_bar);

            tvSumTotal.setText(sumTotal.toPlainString());
            if (budget.getSumForPeriod() != null && !BigDecimal.ZERO.equals(budget.getSumForPeriod())) {
                BigDecimal sum = sumTotal;
                if (sum.compareTo(BigDecimal.ZERO) < 0) sum = sum.negate();
                BigDecimal asPercent = sum.multiply(BigDecimal.valueOf(100)).divide(budget.getSumForPeriod(), RoundingMode.CEILING);
                statusBar.setProgress(asPercent.intValue());
                tvPercent.setText(asPercent.intValue() + "%");
            } else {
                statusBar.setVisibility(View.GONE);
                tvPercent.setVisibility(View.GONE);
            }

            if (budget.isPeriod(DAY) && budget.getSumForPeriod() == null) {
                tvDate.setText(Formatters.formatDDMMYYYY(dateRange.getEndDate()));
            } else {
                tvDate.setText(budget.getName());
            }
        }

        @Override
        public Object getData() {
            return null;
        }


        @Override
        public int compareTo(ListBudgetDataView o) {
            int otherKey = o.budget.getPeriod().getKey();
            int thisKey = budget.getPeriod().getKey();
            return Integer.compare(otherKey, thisKey);
        }
    }

    private void newListView(List<Transaction> sortedTransactions, List<Budget> budgets) {
        List<AltRecyclerViewListAdapter.Data> listViewData = new LinkedList<>();
        sortedTransactions.forEach(e -> listViewData.add(new ListItemDataView(e, navController)));

        List<AltRecyclerViewListAdapter.Data> budgetsViewList = new LinkedList<>();
        boolean isAddSimpleDaysStat = true;
        if (isAddSimpleDaysStat) { //adding simple elements for every day
            Budget simpleDay = new Budget();
            simpleDay.setPeriod(DAY);
            budgets.add(simpleDay);
        }
        for (Budget budget : budgets) {
            if (budget.isPeriod(DAY)) {
                Date date = new Date();
                ListBudgetDataView itemBudget = new ListBudgetDataView(budget, DateUtil.getDayDateRange(date));
                for (Transaction trn : sortedTransactions) {
                    if (!itemBudget.inRange(trn.getDateCreate())) {
                        if (!BigDecimal.ZERO.equals(itemBudget.sumTotal)) {
                            budgetsViewList.add(itemBudget);
                        }
                        do {
                            date = DateUtil.getPreviousDay(date);
                            itemBudget = new ListBudgetDataView(budget, DateUtil.getDayDateRange(date));
                        } while (!itemBudget.inRange(trn.getDateCreate()));
                    }
                    itemBudget.addSumTotal(new BigDecimal(trn.getSum()));
                }
                if (sortedTransactions.size() > 0) {
                    budgetsViewList.add(itemBudget);
                }
            }
            if (budget.isPeriod(WEEK)) {
                int year = DateUtil.getCurrentYear();
                int week = DateUtil.getCurrentWeek();
                ListBudgetDataView itemBudget = new ListBudgetDataView(budget, DateUtil.getWeekDateRange(year, week));
                for (Transaction trn : sortedTransactions) {
                    if (!itemBudget.inRange(trn.getDateCreate())) {
                        if (!BigDecimal.ZERO.equals(itemBudget.sumTotal)) {
                            budgetsViewList.add(itemBudget);
                        }
                        do {
                            week--;
                            if (week <= 0) {
                                year--;
                                week = DateUtil.getMaxWeekOfYear(year);
                            }
                            itemBudget = new ListBudgetDataView(budget, DateUtil.getWeekDateRange(year, week));
                        } while (!itemBudget.inRange(trn.getDateCreate()));
                    }
                    itemBudget.addSumTotal(new BigDecimal(trn.getSum()));
                }
                if (sortedTransactions.size() > 0) {
                    budgetsViewList.add(itemBudget);
                }
            }
            if (budget.isPeriod(MONTH)) {
                int year = DateUtil.getCurrentYear();
                int month = DateUtil.getCurrentMonth();
                ListBudgetDataView itemBudget = new ListBudgetDataView(budget, DateUtil.getMonthDateRange(year, month));
                for (Transaction trn : sortedTransactions) {
                    if (!itemBudget.inRange(trn.getDateCreate())) {
                        if (!BigDecimal.ZERO.equals(itemBudget.sumTotal)) {
                            budgetsViewList.add(itemBudget);
                        }
                        do {
                            month--;
                            if (month <= 0) {
                                year--;
                                month = 12;
                            }
                            itemBudget = new ListBudgetDataView(budget, DateUtil.getMonthDateRange(year, month));
                        } while (!itemBudget.inRange(trn.getDateCreate()));
                    }
                    itemBudget.addSumTotal(new BigDecimal(trn.getSum()));
                }
                if (sortedTransactions.size() > 0) {
                    budgetsViewList.add(itemBudget);
                }
            }
            if (budget.isPeriod(QUARTER)) {
                int year = DateUtil.getCurrentYear();
                int quart = DateUtil.getCurrentQuarter();
                ListBudgetDataView itemBudget = new ListBudgetDataView(budget, DateUtil.getQuarterDateRange(year, quart));
                for (Transaction trn : sortedTransactions) {
                    if (!itemBudget.inRange(trn.getDateCreate())) {
                        if (!BigDecimal.ZERO.equals(itemBudget.sumTotal)) {
                            budgetsViewList.add(itemBudget);
                        }
                        do {
                            quart--;
                            if (quart <= 0) {
                                year--;
                                quart = 4;
                            }
                            itemBudget = new ListBudgetDataView(budget, DateUtil.getQuarterDateRange(year, quart));
                        } while (!itemBudget.inRange(trn.getDateCreate()));
                    }
                    itemBudget.addSumTotal(new BigDecimal(trn.getSum()));
                }
                if (sortedTransactions.size() > 0) {
                    budgetsViewList.add(itemBudget);
                }
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
            if (compare != 0 && DateUtil.isSameDay(date1, date2)) {
                compare = 0;
            }
            if (compare == 0) {
                boolean e1isListBudgetData = e1 instanceof ListBudgetDataView;
                boolean e2isListBudgetData = e2 instanceof ListBudgetDataView;
                if (e1isListBudgetData && e2isListBudgetData) {
                    compare = ((ListBudgetDataView) e1).compareTo((ListBudgetDataView) e2);
                }
                if (e1isListBudgetData != e2isListBudgetData) {
                    compare = e2isListBudgetData ? 1 : -1;
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