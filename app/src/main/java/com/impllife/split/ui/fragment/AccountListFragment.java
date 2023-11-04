package com.impllife.split.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.impllife.split.R;
import com.impllife.split.data.constant.DefaultAccountImg;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.custom.component.BaseView;

import java.util.List;

import static com.impllife.split.data.constant.Constant.ENTITY_ID;
import static com.impllife.split.service.util.Util.bundle;

public class AccountListFragment extends NavFragment {
    private DataService dataService = DataService.getInstance();
    private LinearLayout list;
    private View btnNew;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_list, container, false);
        setNavTitle("Accounts");

        init(view);

        runAsync(() -> {
            List<Account> all = dataService.getAllAccounts();
            view.post(() -> {
                for (Account account : all) {
                    BaseView cardView = new BaseView(inflater, R.layout.view_account_list_item, list);
                    cardView.setTextViewById(R.id.tv_name, account.getName());
                    cardView.setTextViewById(R.id.tv_amount, String.valueOf(account.getAmount()));
                    cardView.setOnClickListener(v -> navController.navigate(R.id.fragment_account_setup, bundle(ENTITY_ID, account.getId())));

                    ImageView imgCard = cardView.findViewById(R.id.img_card);
                    DefaultAccountImg parsed = DefaultAccountImg.parse(account.getImgName());
                    if (parsed != null) {
                        imgCard.setImageResource(parsed.id);
                    }
                    list.addView(cardView.getRoot());
                }
            });
        });

        return view;
    }

    private void init(View view) {
        list = view.findViewById(R.id.list);
        btnNew = view.findViewById(R.id.btn_new);

        btnNew.setOnClickListener(v -> {
            navController.navigate(R.id.fragment_account_setup);
        });
    }
}