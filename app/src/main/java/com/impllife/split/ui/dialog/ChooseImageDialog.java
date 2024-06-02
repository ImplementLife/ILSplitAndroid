package com.impllife.split.ui.dialog;

import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import com.impllife.split.R;
import com.impllife.split.data.constant.DefaultUserIcon;
import com.impllife.split.ui.custom.CustomDialog;
import com.impllife.split.ui.custom.adapter.GridListAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static android.view.ViewGroup.LayoutParams;

public class ChooseImageDialog extends CustomDialog {
    private final Consumer<Integer> onImageChosen;

    public ChooseImageDialog(Consumer<Integer> onImageChosen) {
        this.onImageChosen = onImageChosen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_image);
        getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        GridView grid = findViewById(R.id.grid);
        List<Integer> dat = Arrays.stream(DefaultUserIcon.values())
            .filter(e -> e.id != R.drawable.ic_png_contact_default)
            .map(e -> e.id).collect(Collectors.toList());
        GridListAdapter<Integer> adapter = new GridListAdapter<>(dat, (data, parent) -> {
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setImageResource(data);
            imageView.setOnClickListener(v -> {
                onImageChosen.accept(data);
                dismiss();
            });
            return imageView;
        });
        grid.setAdapter(adapter);
    }
}
