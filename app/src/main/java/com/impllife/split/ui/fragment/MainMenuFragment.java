package com.impllife.split.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import com.impllife.split.R;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.view.BtnMainMenu;

import static android.view.Gravity.FILL_HORIZONTAL;

public class MainMenuFragment extends NavFragment {
    private GridLayout grid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_main_menu, inflater, container);
        MainActivity.getInstance().hideHead();

        grid = view.findViewById(R.id.grid);

        for (MainMenuButtonDefinition btn : MainMenuButtonDefinition.values()) {
            BtnMainMenu btnView = new BtnMainMenu(btn, inflater, grid);
            btnView.setOnClickListener(v -> btn.getAction().accept(navController));
            setBtn(btnView.getRoot(), btn.getDefaultRow(), btn.getDefaultCol());
        }
        return view;
    }

    private void setBtn(View view, int row, int col) {
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
            GridLayout.spec(row, 1f),
            GridLayout.spec(col, 1f));
        layoutParams.setGravity(FILL_HORIZONTAL);
        layoutParams.height = 100;

        grid.addView(view, layoutParams);
    }
}