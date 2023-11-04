package com.impllife.split.ui.dialog;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import com.impllife.split.R;
import com.impllife.split.ui.custom.CustomDialog;

public class QuickAddAllNotify extends CustomDialog {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_quick_add_all_notify);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }
}
