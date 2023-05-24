package com.impllife.split.ui.fragment;

import android.widget.LinearLayout;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.custom.component.BaseView;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.view.TransactionListItem;
import com.impllife.split.ui.view.TransactionListItemDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.impllife.split.data.constant.Constant.ENTITY_ID;
import static com.impllife.split.data.constant.Constant.FOCUS_NEED;
import static com.impllife.split.service.Util.*;

public class TransactionListFragment extends NavFragment {
    private DataService dataService = DataService.getInstance();
    private LinearLayout listItems;

    public TransactionListFragment() {
        super(R.layout.fragment_transaction_list, "Transactions");
    }

    @Override
    protected void init() {
        listItems = findViewById(R.id.list_items);
        findViewById(R.id.btn_new).setOnClickListener(v -> {
            navController.navigate(R.id.fragment_transaction_setup, bundle(FOCUS_NEED, true));
        });

        runAsync(() -> {
            List<Transaction> allTransactions = dataService.getAllTransactions();
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