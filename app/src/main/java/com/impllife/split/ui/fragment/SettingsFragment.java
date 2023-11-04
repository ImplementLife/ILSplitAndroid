package com.impllife.split.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import com.impllife.split.R;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.custom.component.NavFragment;

import static com.impllife.split.data.constant.Constant.DELETE_NOTIFY_AFTER_PROCESS;

public class SettingsFragment extends NavFragment {
    private CheckBox cbDel;
    public SettingsFragment() {
        super(R.layout.fragment_settings, "Settings");
    }

    @Override
    protected void init() {
        cbDel = findViewById(R.id.cb_delete_notify_after_process);
        cbDel.setChecked(getPreferenceValue(DELETE_NOTIFY_AFTER_PROCESS));
        cbDel.setOnCheckedChangeListener((v, isChecked) -> {
            writeToPreference(DELETE_NOTIFY_AFTER_PROCESS, isChecked);
        });
    }


    public static boolean getPreferenceValue(String tag) {
        SharedPreferences sp = MainActivity.getInstance().getSharedPreferences("il_settings", Context.MODE_PRIVATE);
        return sp.getBoolean(tag,true);
    }

    public static void writeToPreference(String tag, boolean value) {
        SharedPreferences.Editor editor = MainActivity.getInstance().getSharedPreferences("il_settings",Context.MODE_PRIVATE).edit();
        editor.putBoolean(tag, value);
        editor.apply();
    }
}