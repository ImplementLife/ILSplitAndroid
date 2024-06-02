package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.impllife.split.R;
import com.impllife.split.data.constant.DefaultAccountImg;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.custom.component.BaseView;

import static com.impllife.split.data.constant.Constant.*;
import static com.impllife.split.service.util.Util.bundle;

public class AccountChooseImgFragment extends NavFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_account_choose_img, inflater, container);
        setNavTitle("Account Image");

        ImageView imgPreview = findViewById(R.id.img_preview);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String name = arguments.getString(NAME);
            DefaultAccountImg parsed = DefaultAccountImg.parse(name);
            if (parsed != null) {
                imgPreview.setImageResource(parsed.id);
                getParentFragmentManager().setFragmentResult(FRAGMENT_RESULT_KEY, bundle(NAME, parsed.name));
            }
        }



        GridView grid = findViewById(R.id.grid);
        ArrayAdapter<DefaultAccountImg> adapter = new ArrayAdapter<DefaultAccountImg>(
            MainActivity.getInstance(), R.layout.view_btn_main_menu, DefaultAccountImg.values()
        ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                DefaultAccountImg item = getItem(position);
                BaseView baseView = new BaseView(inflater, R.layout.view_account_card_grid_item, grid);
                ImageView img = baseView.findViewById(R.id.img_people_icon);
                img.setImageResource(item.id);
                baseView.setOnClickListener(v -> {
                    imgPreview.setImageResource(item.id);
                    getParentFragmentManager().setFragmentResult(FRAGMENT_RESULT_KEY, bundle(NAME, item.name));
                });
                return baseView.getRoot();
            }
        };

        grid.setAdapter(adapter);

        return view;
    }
}