package com.impllife.split.ui.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Transaction;

public class TransactionListItem extends BaseView {
    private TextView tvDscr;
    private TextView tvSum;
    private ImageView image;

    public TransactionListItem(LayoutInflater inflater, ViewGroup rootForThis, Transaction transaction) {
        super(inflater, R.layout.view_transactoin_list_item, rootForThis);

        init();
        setData(transaction);
    }

    private void init() {
        tvSum = findViewById(R.id.tv_sum);
        tvDscr = findViewById(R.id.tv_dscr);
        image = findViewById(R.id.image);
    }

    public void setData(Transaction transaction) {
        tvSum.setText(transaction.getSum());
        tvDscr.setText(transaction.getDescription());
    }

}
