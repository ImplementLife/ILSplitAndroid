package com.impllife.split.ui.custom;

import android.app.Dialog;
import com.impllife.split.ui.MainActivity;

public class CustomDialog extends Dialog {
    public CustomDialog() {
        super(MainActivity.getInstance());
    }

}
