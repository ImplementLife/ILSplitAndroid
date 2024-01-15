package com.impllife.split.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.ui.custom.component.BaseView;

@Deprecated
public class TransactionListItem extends BaseView {
    private TextView tvDscr;
    private TextView tvSum;
    private ImageView image;
    private Transaction transaction;

    public TransactionListItem(LayoutInflater inflater, ViewGroup rootForThis, Transaction transaction) {
        super(inflater, R.layout.view_transactoin_list_item, rootForThis);

        init();
        setData(transaction);

    }

    public void setOnClick(View.OnClickListener listener) {
        root.setOnClickListener(listener);
    }

    private void init() {
        tvSum = findViewById(R.id.tv_sum);
        tvDscr = findViewById(R.id.tv_dscr);
        image = findViewById(R.id.image);
    }

    public void setData(Transaction transaction) {
        this.transaction = transaction;
        tvSum.setText(transaction.getSum());
        tvDscr.setText(transaction.getDescription());
    }

}
