package com.impllife.split.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.constant.DefaultUserIcon;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.ui.custom.component.BaseView;

import java.math.BigDecimal;

import static com.impllife.split.service.util.Util.isBlank;

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

        People fromPeople = transaction.getFromPeople();
        Account fromAccount = transaction.getFromAccount();

        People toPeople = transaction.getToPeople();
        Account toAccount = transaction.getToAccount();

        {
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
                    image.setImageResource(R.drawable.ic_svg_transaction_alt);
                } else {
                    image.setImageResource(R.drawable.ic_svg_transaction);
                }
            }

            if (isBlank(transaction.getDescription())) {
                if (peopleForIcon != null) tvDscr.setText(peopleForIcon.getPseudonym());
                else tvDscr.setText("");
            } else {
                tvDscr.setText(transaction.getDescription());
            }
        }
    }

}
