package com.impllife.split.ui.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.impllife.split.R;

public class BtnMainMenu extends BaseView {
    private ImageView img;
    private TextView name;

    public BtnMainMenu(LayoutInflater inflater, ViewGroup rootForThis) {
        super(inflater, R.layout.view_btn_main_menu, rootForThis);
        init();
    }

    public void init() {
        img = findViewById(R.id.img);
        name = findViewById(R.id.tv_name);
    }

    public void setImage(int id) {
        img.setImageResource(id);
    }

    public void setName(String name) {
        this.name.setText(name);
    }
}
