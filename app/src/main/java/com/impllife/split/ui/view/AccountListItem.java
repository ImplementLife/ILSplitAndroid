package com.impllife.split.ui.view;

import android.widget.ImageView;
import androidx.navigation.NavController;
import com.impllife.split.R;
import com.impllife.split.data.constant.DefaultAccountImg;
import com.impllife.split.data.jpa.entity.Account;
import com.impllife.split.ui.custom.adapter.UniversalRVListAdapter;
import com.impllife.split.ui.custom.component.BaseView;

import static com.impllife.split.data.constant.Constant.ENTITY_ID;
import static com.impllife.split.service.util.Util.bundle;

public class AccountListItem extends UniversalRVListAdapter.ModelViewData<Account> {
    private final NavController navController;

    public AccountListItem(NavController navController, Account account) {
        super(R.layout.view_account_list_item, account);
        this.navController = navController;
    }

    @Override
    public void bindData(BaseView view) {
        Account data = getData();
        view.setTextViewById(R.id.tv_name, data.getName());
        view.setTextViewById(R.id.tv_amount, String.valueOf(data.getAmount()));
        view.setOnClickListener(v -> navController.navigate(R.id.fragment_account_setup, bundle(ENTITY_ID, data.getId())));

        ImageView imgCard = view.findViewById(R.id.img_card);
        DefaultAccountImg parsed = DefaultAccountImg.parse(data.getImgName());
        if (parsed != null) {
            imgCard.setImageResource(parsed.id);
        }
    }
}
