package com.impllife.split.ui.fragment;

import androidx.navigation.NavController;
import com.impllife.split.R;

import java.util.function.Consumer;

public enum MainMenuButtonDefinition {
    BTN_CONTACTS("Contacts", 0, 0, R.drawable.ic_svg_group, n -> n.navigate(R.id.fragment_contacts)),
    BTN_QR("My link qr", 0, 1, -1, n -> {}),
    BTN_QR_SCANNER("QR Scan", 0, 2, -1, n -> {}),

    BTN_SETTINGS("Settings", 1, 0, -1, n -> {}),
    BTN_NOTIFICATIONS("Notify's", 1, 1, R.drawable.ic_svg_notifications, n -> n.navigate(R.id.fragment_notification)),
    BTN_DISCOUNTS("Discounts", 1, 2, -1, n -> {}),

    BTN_BILLINGS("Billings", 2, 0, -1, n -> {}),
    BTN_REQUISITIONS("Requisitions", 2, 1, -1, n -> {}),
    BTN_NEW_REQUISITION("New Req", 2, 2, -1, n -> {}),

    BTN_BUDGET("Budget", 3, 0, R.drawable.ic_svg_money, n -> n.navigate(R.id.fragment_budget_menu)),
    BTN_TRANSACTIONS("Transact's", 3, 1, R.drawable.ic_svg_history, n -> n.navigate(R.id.fragment_transactions_list)),
    BTN_NEW_TRANSACTION("New Trn", 3, 2, R.drawable.ic_svg_receipt, n -> n.navigate(R.id.fragment_transaction_setup)),

    //BTN_NFC_PAY("NFC Pay", 4, 2, -1, n -> {}),

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
    private String name;
    private int imgId;
    private Consumer<NavController> action;

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
