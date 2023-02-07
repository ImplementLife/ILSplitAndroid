package com.impllife.split.ui;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.Rec;
import com.impllife.split.service.ComService;
import com.impllife.split.ui.fragments.TestFragment;

public class CardView {
    public CardView(Rec e, View view, Fragment owner) {
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
                if (owner instanceof TestFragment) {
                    ((TestFragment) owner).read();
                }
            }
        });
        ((TextView) view.findViewById(R.id.textView_name)).setText(e.getId() + " " + e.getName());
        ((TextView) view.findViewById(R.id.textView_email)).setText(e.getEmail());
    }
}
