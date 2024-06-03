package com.impllife.split.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.impllife.split.R;

import java.util.Objects;

import static com.impllife.split.data.constant.Constant.ACTION;
import static com.impllife.split.data.constant.Constant.ACTION_START_NOTIFY_FRAGMENT;

public class MainActivity extends AppCompatActivity {
    public static MainActivity getInstance() {
        return instance;
    }
    @SuppressLint("StaticFieldLeak")
    private static MainActivity instance;

    public NavController navController;

    private View headBar;
    private TextView headTitle;
    private View btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity", "onCreate");

        //Looks like a fix problem with inet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //hide top bar with app name
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);
        instance = this;

        btnInfo = findViewById(R.id.btn_info);
        headBar = findViewById(R.id.head);
        headTitle = findViewById(R.id.tv_title);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        findViewById(R.id.btn_back).setOnClickListener(v -> navController.navigateUp());

        String filesPath = getFilesDir().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bundleProcessing();
    }

    private void bundleProcessing() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String act = extras.getString(ACTION);
            if (ACTION_START_NOTIFY_FRAGMENT.equals(act)) {
                navController.navigate(R.id.fragment_notify_list);
                extras.remove(act);
            }
        }
    }

    public void setHeadTitle(String title) {
        headTitle.setText(title);
    }
    public void hideHead() {
        headBar.setVisibility(View.GONE);
    }
    public void showHead() {
        headBar.setVisibility(View.VISIBLE);
    }
    public void hideInfoBtn() {
        btnInfo.setVisibility(View.INVISIBLE);
    }
    public void showInfoBtn() {
        btnInfo.setVisibility(View.VISIBLE);
    }
    public void setInfoBtnAction(Runnable exe) {
        btnInfo.setOnClickListener(v -> exe.run());
    }

    public void hideKeyboard() {
        View view = MainActivity.getInstance().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public void showKeyboard(View view) {
        if (view != null && view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}