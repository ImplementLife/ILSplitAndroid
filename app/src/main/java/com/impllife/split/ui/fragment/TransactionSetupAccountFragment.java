package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.constant.DefaultAccountImg;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.ui.custom.component.BaseFragment;

public class TransactionSetupAccountFragment extends BaseFragment {
    private Account account;
    private ImageView imgCard;
    private TextView tvName;
    private TextView tvAmount;

    public TransactionSetupAccountFragment(Account account) {
        this.account = account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_transaction_setup_account, inflater, container);
        init();

        String imgName = account.getImgName();
        DefaultAccountImg defaultAccountImg = DefaultAccountImg.parse(imgName);
        if (defaultAccountImg != null) {
            imgCard.setImageResource(defaultAccountImg.id);
        }

        tvName.setText(account.getName());
        tvAmount.setText(Double.toString(account.getAmount()));
        return view;
    }

    protected void init() {
        imgCard = findViewById(R.id.img_card);
        tvName = findViewById(R.id.tv_name);
        tvAmount = findViewById(R.id.tv_amount);
    }
}