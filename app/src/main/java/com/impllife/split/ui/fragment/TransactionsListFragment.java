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
import java.util.concurrent.atomic.AtomicReference;

import static com.impllife.split.service.Util.equalsDateByDMY;

public class TransactionsListFragment extends NavFragment {
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
        view.findViewById(R.id.btn_new).setOnClickListener(v -> navController.navigate(R.id.fragment_transaction_new));

        runAsync(() -> {
            List<Transaction> allTransactions = DataService.getInstance().getAllTransactions();
            view.post(() -> updateView(createListView(allTransactions)));
        });

        return view;
    }

    private List<BaseView> createListView(List<Transaction> sortedTransactions) {
        List<BaseView> viewList = new ArrayList<>();
        Date startDate = sortedTransactions.get(0).getDateCreate();
        AtomicReference<Date> currentProcessingDate = new AtomicReference<>(startDate);
        viewList.add(new TransactionListItemDate(inflater, listItems, startDate));

        sortedTransactions.forEach(transaction -> {
            calendar.setTime(transaction.getDateCreate());
            calendar.clear(Calendar.HOUR);
            calendar.clear(Calendar.MINUTE);
            calendar.clear(Calendar.SECOND);
            calendar.clear(Calendar.MILLISECOND);

            Date date = calendar.getTime();
            if (!equalsDateByDMY(currentProcessingDate.get(), date)) {
                viewList.add(new TransactionListItemDate(inflater, listItems, date));
                currentProcessingDate.set(date);
            }

            viewList.add(new TransactionListItem(inflater, listItems, transaction));
        });

        return viewList;
    }

    private void updateView(List<BaseView> list) {
        listItems.removeAllViews();
        list.forEach(e -> listItems.addView(e.getRoot()));
    }

}