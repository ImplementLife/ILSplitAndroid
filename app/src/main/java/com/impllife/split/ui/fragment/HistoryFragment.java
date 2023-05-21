package com.impllife.split.ui.fragment;

import android.widget.GridView;
import androidx.navigation.NavController;
import com.impllife.split.R;
import com.impllife.split.ui.custom.adapter.ListAdapter;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.custom.component.BaseView;

import java.util.function.Consumer;

public class HistoryFragment extends NavFragment {
    private GridView grid;
    public HistoryFragment() {
        super(R.layout.fragment_history, "History");
    }
    private enum MenuBtn {
        BTN_TRANSACTIONS("Transact's", R.drawable.ic_svg_history, n -> n.navigate(R.id.fragment_transactions_list)),
        BTN_REQUISITIONS("Requisitions", R.drawable.ic_svg_cancel, n -> {}),
        BTN_BILLINGS("Billings", R.drawable.ic_svg_cancel, n -> {}),
        ;
        private final String name;
        private final int imgId;
        private final Consumer<NavController> action;

        MenuBtn(String name, int imgId, Consumer<NavController> action) {
            this.name = name;
            this.action = action;
            this.imgId = imgId;
        }
    }
    @Override
    protected void init() {
        grid = findViewById(R.id.grid);
        ListAdapter<MenuBtn> adapter = new ListAdapter<>(MenuBtn.values(), (data, parent) -> {
            BaseView view = new BaseView(inflater, R.layout.view_btn_main_menu, parent);
            view.setTextViewById(R.id.tv_name, data.name);
            view.setImgResById(R.id.img, data.imgId);
            view.setOnClickListener(v -> data.action.accept(navController));
            return view.getRoot();
        });
        grid.setAdapter(adapter);
    }
}