package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.view.BaseView;
import com.impllife.split.ui.view.TransactionListItem;
import com.impllife.split.ui.view.TransactionListItemDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.impllife.split.service.Util.equalsDateByDMY;
import static com.impllife.split.service.Util.isBlank;

public class TransactionsListFragment extends NavFragment {
    private DataService dataService = DataService.getInstance();
    private LinearLayout listItems;
    private Calendar calendar;
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.fragment_transactions_list, container, false);
        setNavTitle("Transactions");

        listItems = view.findViewById(R.id.list_items);
        calendar = Calendar.getInstance();
        view.findViewById(R.id.btn_new).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("focus_need", true);
            navController.navigate(R.id.fragment_transaction_setup, bundle);
        });

        runAsync(() -> {
            List<Transaction> allTransactions = dataService.getAllTransactions();
            view.post(() -> updateView(createListView(allTransactions)));
        });

        return view;
    }

    private List<BaseView> createListView(List<Transaction> sortedTransactions) {
        List<BaseView> result = new ArrayList<>();
        if (sortedTransactions.size() == 0) return result;

        Date startDate = sortedTransactions.get(0).getDateCreate();
        Date currentProcessingDay = startDate;

        TransactionListItemDate currentViewDate = new TransactionListItemDate(inflater, listItems, startDate);
        double currentSumTotal = 0;

        result.add(currentViewDate);

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
            currentViewDate.setData(String.valueOf(currentSumTotal));

            TransactionListItem transactionListItem = new TransactionListItem(inflater, listItems, transaction);
            Bundle bundle = new Bundle();
            bundle.putInt("trn_id", transaction.getId());
            transactionListItem.setOnClick(v -> navController.navigate(R.id.fragment_transaction_setup, bundle));
            result.add(transactionListItem);
        }

        return result;
    }

    private void updateView(List<BaseView> list) {
        listItems.removeAllViews();
        list.forEach(e -> listItems.addView(e.getRoot()));
    }

}