package com.impllife.split.ui.dialog;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import com.impllife.split.R;
import com.impllife.split.ui.custom.CustomDialog;

public class ChooseTrnTemplateDialog extends CustomDialog {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_transaction_template);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
