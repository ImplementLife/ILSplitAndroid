package com.impllife.split.ui.fragments;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.service.ComService;
import com.impllife.split.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionsListFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions_list, container, false);
        MainActivity.getInstance().showHead();
        MainActivity.getInstance().setHeadTitle("Transactions");

        view.findViewById(R.id.btn_new).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.fragment_transaction_new);
        });

        Thread thread = new Thread(() -> {
            List<Transaction> allTransactions = ComService.getInstance().getAllTransactions();
            //allTransactions.sort((t1, t2) -> (int) (t1.getDateCreate() - t2.getDateCreate()));
            view.post(() -> {
                allTransactions.forEach(e -> {
                    TextView textView = new TextView(inflater.getContext());
                    textView.setText(e.getId() + " : " + e.getSum() + " $   " + new SimpleDateFormat("dd.MM.YYYY").format(new Date(e.getDateCreate())));
                    ((LinearLayout) view.findViewById(R.id.list_items)).addView(textView);
                });
            });
        });
        thread.setDaemon(true);
        thread.start();

        return view;
    }

}