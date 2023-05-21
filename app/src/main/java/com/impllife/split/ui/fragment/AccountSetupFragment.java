package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.impllife.split.R;
import com.impllife.split.data.constant.DefaultAccountImg;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.custom.component.NavFragment;

import static com.impllife.split.data.constant.Constant.*;
import static com.impllife.split.data.constant.DefaultAccountImg.img_1;
import static com.impllife.split.service.Util.bundle;

public class AccountSetupFragment extends NavFragment {
    private DataService dataService = DataService.getInstance();
    private Button btnOk;
    private EditText etName;
    private EditText etAmount;
    private ImageView imgCard;
    private String imgName = img_1.name;

    private Account account;
    private boolean update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_account_setup, inflater, container);
        setNavTitle("Account Setup");

        init(view);

        Bundle arguments = getArguments();
        if (arguments != null) {
            int entityId = arguments.getInt(ENTITY_ID, 0);
            runAsync(() -> {
                account = dataService.getDb().getAccountDao().findById(entityId);
                update = account != null;
                if (update) {
                    etName.setText(account.getName());
                    etAmount.setText(Double.toString(account.getAmount()));
                    root.post(() -> {
                        DefaultAccountImg parsed = DefaultAccountImg.parse(account.getImgName());
                        if (parsed != null) {
                            imgCard.setImageResource(parsed.id);
                            imgName = parsed.name;
                        }
                    });
                }
            });
        }
        getParentFragmentManager().setFragmentResultListener(FRAGMENT_RESULT_KEY, this, (key, bundle) -> {
            if (FRAGMENT_RESULT_KEY.equals(key)) {
                String name = bundle.getString(NAME);
                DefaultAccountImg parsed = DefaultAccountImg.parse(name);
                root.post(() -> {
                    if (parsed != null) {
                        imgCard.setImageResource(parsed.id);
                        imgName = parsed.name;
                    }
                });
            }
        });

        imgCard.setOnClickListener(v -> {
            if (account != null) {
                navController.navigate(R.id.fragment_account_choose_img, bundle(NAME, account.getImgName()));
            } else {
                navController.navigate(R.id.fragment_account_choose_img);
            }
        });

        btnOk.setOnClickListener(v -> {
            if (!update) account = new Account();
            account.setAmount(Double.parseDouble(String.valueOf(etAmount.getText())));
            account.setName(String.valueOf(etName.getText()));
            account.setImgName(imgName);
            runAsync(() -> {
                if (!update) {
                    dataService.getDb().getAccountDao().insert(account);
                } else {
                    dataService.getDb().getAccountDao().update(account);
                }
                view.post(() -> navController.navigateUp());
            });
        });
        return view;
    }

    private void init(View view) {
        btnOk = view.findViewById(R.id.btn_ok);
        etName = view.findViewById(R.id.et_name);
        etAmount = view.findViewById(R.id.et_amount);
        imgCard = view.findViewById(R.id.img_card);
    }
}