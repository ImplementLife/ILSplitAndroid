package com.impllife.split.ui.dialog;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import androidx.navigation.NavController;
import com.impllife.split.R;
import com.impllife.split.ui.custom.CustomDialog;

public class ChooseQrAction extends CustomDialog {
    protected NavController navController;

    public ChooseQrAction(NavController navController) {
        this.navController = navController;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_qr_action);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }
}
