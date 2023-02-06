package com.impllife.split.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Rec;
import com.impllife.split.service.ComService;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static MainActivity instance;

    private LinearLayout linearLayout;

    @SuppressLint("SetTextI18n")
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
        linearLayout = findViewById(R.id.LinearLayout);

        TextView field_email = findViewById(R.id.field_email);
        TextView field_name = findViewById(R.id.field_name);
        AtomicInteger i = new AtomicInteger();
        findViewById(R.id.add).setOnClickListener(v -> {
            Rec rec = new Rec();
            rec.setName(field_name.getText().toString() + " " + i.incrementAndGet());
            rec.setEmail(field_email.getText().toString());
            ComService.getInstance().add(rec);
            read();
        });
        findViewById(R.id.read).setOnClickListener(v -> read());
        findViewById(R.id.delete).setOnClickListener(v -> ComService.getInstance().delete());
    }

    private void read() {
        linearLayout.removeAllViews();
        List<Rec> read = ComService.getInstance().read();
        read.forEach(e -> {
            View view = getLayoutInflater().inflate(R.layout.view_rec, linearLayout, false);
            View layout_expand = view.findViewById(R.id.layout_expand);
            layout_expand.setVisibility(View.GONE);
            view.findViewById(R.id.btn_hide).setOnClickListener(vv -> {
                if (layout_expand.getVisibility() == View.GONE) {
                    layout_expand.setVisibility(View.VISIBLE);
                    ((ImageButton) vv).setImageResource(R.drawable.ic_svg_visibility_off);
                } else {
                    ((ImageButton) vv).setImageResource(R.drawable.ic_svg_visibility);
                    layout_expand.setVisibility(View.GONE);
                }
            });
            view.findViewById(R.id.btn_delete).setOnClickListener(vv -> {
                boolean isDelete = ComService.getInstance().deleteById(e.getId());
                if (isDelete) {
                    read();
                }
            });
            ((TextView) view.findViewById(R.id.textView_name)).setText(e.getId() + " " + e.getName());
            ((TextView) view.findViewById(R.id.textView_email)).setText(e.getEmail());

            linearLayout.addView(view);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainActivity", "onRestart");
    }
}