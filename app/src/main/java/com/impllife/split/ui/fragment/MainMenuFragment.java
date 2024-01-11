package com.impllife.split.ui.fragment;

import android.widget.GridView;
import androidx.navigation.NavController;
import com.impllife.split.R;
import com.impllife.split.ui.custom.adapter.GridListAdapter;
import com.impllife.split.ui.custom.component.BaseView;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.dialog.ChooseQrAction;
import com.impllife.split.ui.dialog.ChooseTrnTemplateDialog;

import java.util.function.Consumer;

public class MainMenuFragment extends NavFragment {
    private GridView grid;

    public MainMenuFragment() {
        super(R.layout.fragment_main_menu);
    }

    @Override
    protected void init() {
        grid = findViewById(R.id.grid);
        GridListAdapter<MainMenuButtonDefinition> adapter = new GridListAdapter<>(MainMenuButtonDefinition.values(), (data, parent) -> {
            BaseView view = new BaseView(R.layout.view_btn_main_menu, grid);

            view.setOnClickListener(v -> data.getAction().accept(navController));
            view.setTextViewById(R.id.tv_name, data.getName());
            view.setImgResById(R.id.img, data.getImgId());
            return view.getRoot();
        });
        grid.setAdapter(adapter);
    }

    public enum MainMenuButtonDefinition {
        BTN_SETTINGS("Settings", 0, 0, R.drawable.ic_svg_settings, n -> n.navigate(R.id.fragment_settings)),
        BTN_CONTACTS("Contacts", 0, 1, R.drawable.ic_svg_group, n -> n.navigate(R.id.fragment_contact)),
        BTN_QR_SCANNER("QR", 0, 2, R.drawable.ic_svg_qr_code, n -> {new ChooseQrAction(n).show();}),
        BTN_HISTORY("History", 1, 0, R.drawable.ic_svg_history, n -> n.navigate(R.id.fragment_history)),
        BTN_NOTIFICATIONS("Notify's", 1, 1, R.drawable.ic_svg_notifications, n -> n.navigate(R.id.fragment_notify_list)),
        BTN_NEW_SPLIT("Split", 1, 2, R.drawable.ic_svg_groups, n -> n.navigate(R.id.fragment_requisition_setup)),
        BTN_BUDGET("Budget", 2, 0, R.drawable.ic_svg_money, n -> n.navigate(R.id.fragment_budget_menu)),
        BTN_NEW_TRANSACTION_BY_TEMPLATE("Templates", 2, 1, R.drawable.ic_svg_custom_grid, n -> {
            new ChooseTrnTemplateDialog().show();
        }),
        BTN_NEW_TRANSACTION("Transact", 2, 2, R.drawable.ic_svg_trandaction, n -> n.navigate(R.id.fragment_transaction_setup)),
        ;

        MainMenuButtonDefinition(String name, int defaultRow, int defaultCol, int imgId, Consumer<NavController> action) {
            this.name = name;
            this.imgId = imgId;
            this.action = action;
            this.defaultCol = defaultCol;
            this.defaultRow = defaultRow;
        }

        private final int defaultCol;
        private final int defaultRow;
        private final String name;
        private final int imgId;
        private final Consumer<NavController> action;

        public int getDefaultCol() {
            return defaultCol;
        }
        public int getDefaultRow() {
            return defaultRow;
        }
        public String getName() {
            return name;
        }
        public Consumer<NavController> getAction() {
            return action;
        }
        public int getImgId() {
            return imgId;
        }
    }
}