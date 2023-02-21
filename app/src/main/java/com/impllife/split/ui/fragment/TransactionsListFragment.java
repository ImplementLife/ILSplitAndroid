package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.service.DataService;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionsListFragment extends NavFragment {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
    private LinearLayout listItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions_list, container, false);
        setNavTitle("Transactions");

        listItems = view.findViewById(R.id.list_items);

        view.findViewById(R.id.btn_new).setOnClickListener(v -> {
            navController.navigate(R.id.fragment_transaction_new);
        });

        Thread thread = new Thread(() -> {
            Map<Integer, DateInfo> trn = new TreeMap<>();
            List<Transaction> allTransactions = DataService.getInstance().getAllTransactions();
            //allTransactions.sort((t1, t2) -> (int) (t1.getDateCreate() - t2.getDateCreate()));
            allTransactions.forEach(e -> {
                Date date = e.getDateCreate();
                Calendar instance = Calendar.getInstance();
                instance.setTime(date);
                int day = instance.get(Calendar.DAY_OF_MONTH);
                int month = instance.get(Calendar.MONTH);
                int year = instance.get(Calendar.YEAR);
                int res = Integer.parseInt(Integer.toString(year) + Integer.toString(month) + Integer.toString(day));
                dateFormat.format(date);
                DateInfo dateInfo = trn.get(res);
                if (dateInfo == null) {
                    dateInfo = new DateInfo(res, dateFormat.format(date));
                    trn.put(res, dateInfo);
                }
                dateInfo.getTransactions().add(e);
            });

            view.post(() -> {
                Set<Integer> keySet = trn.keySet();
                List<Integer> collect = keySet.stream().sorted((v1, v2) -> v2 - v1).collect(Collectors.toList());
                collect.forEach(k -> {
                    DateInfo dateInfo = trn.get(k);
                    TextView textView = new TextView(inflater.getContext());
                    textView.setText(dateInfo.getDate());
                    listItems.addView(textView);

                    dateInfo.getTransactions().forEach(e -> {
                        View itemView = inflater.inflate(R.layout.view_transactoin_list, listItems, false);
                        ((TextView) itemView.findViewById(R.id.tv_sum)).setText(e.getSum());
                        ((TextView) itemView.findViewById(R.id.tv_dscr)).setText(e.getDescription());
                        listItems.addView(itemView);
                    });
                });
            });
        });
        thread.setDaemon(true);
        thread.start();

        return view;
    }

    private static class DateInfo {
        public DateInfo(long id, String date) {
            this.id = id;
            this.date = date;
            this.transactions = new ArrayList<>();
        }

        private long id;
        private String date;
        private List<Transaction> transactions;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<Transaction> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<Transaction> transactions) {
            this.transactions = transactions;
        }
    }

}