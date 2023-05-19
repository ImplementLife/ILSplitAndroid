package com.impllife.split.ui.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.impllife.split.R;
import com.impllife.split.ui.fragment.MainMenuButtonDefinition;

public class BtnMainMenu extends BaseView {
    public BtnMainMenu(MainMenuButtonDefinition item, LayoutInflater inflater, ViewGroup rootForThis) {
        super(inflater, R.layout.view_btn_main_menu, rootForThis);

        if (item.getImgId() != -1) {
            ImageView img = findViewById(R.id.img);
            img.setImageResource(item.getImgId());
        }

        setTextViewById(R.id.tv_name, item.getName());
    }
}
