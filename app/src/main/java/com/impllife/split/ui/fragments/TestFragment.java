package com.impllife.split.ui.fragments;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Rec;
import com.impllife.split.service.ComService;
import com.impllife.split.ui.CardView;
import com.impllife.split.ui.MainActivity;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestFragment extends Fragment {
    private LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        MainActivity.getInstance().showHead();
        MainActivity.getInstance().setHeadTitle("Test");

        linearLayout = view.findViewById(R.id.LinearLayout);

        TextView field_email = view.findViewById(R.id.field_email);
        TextView field_name = view.findViewById(R.id.field_name);
        AtomicInteger i = new AtomicInteger();
        view.findViewById(R.id.add).setOnClickListener(v -> {
            Rec rec = new Rec();
            rec.setName(field_name.getText().toString() + " " + i.incrementAndGet());
            rec.setEmail(field_email.getText().toString());
            ComService.getInstance().add(rec);
            read();
        });
        view.findViewById(R.id.read).setOnClickListener(v -> read());
        view.findViewById(R.id.delete).setOnClickListener(v -> ComService.getInstance().delete());

        return view;
    }

    public void read() {
        linearLayout.removeAllViews();
        List<Rec> read = ComService.getInstance().read();
        read.forEach(e -> {
            View view = getLayoutInflater().inflate(R.layout.view_rec, linearLayout, false);
            new CardView(e, view, this);
            linearLayout.addView(view);
        });
    }
}