package com.impllife.split.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.constant.DefaultUserIcon;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.custom.component.BaseView;
import com.impllife.split.ui.dialog.ChooseImageDialog;

import java.util.function.Consumer;

import static com.impllife.split.service.util.Util.isBlank;

public class PeopleView extends BaseView {
    private final DataService dataService = DataService.getInstance();
    private Consumer<People> btnEditAction;
    private Runnable postDeleteAction;
    private final People people;

    public PeopleView(LayoutInflater inflater, ViewGroup rootForThis, Consumer<People> btnEditAction, People people) {
        super(inflater, R.layout.view_people, rootForThis);
        this.btnEditAction = btnEditAction;
        this.people = people;

        init();
    }

    public PeopleView(LayoutInflater inflater, ViewGroup rootForThis, People people) {
        this(inflater, rootForThis, null, people);
    }

    private void init() {
        View layout_expand = findViewById(R.id.layout_expand);
        layout_expand.setVisibility(View.GONE);

        ImageView imgExpand = findViewById(R.id.img_expand);
        root.setOnClickListener(v -> {
            imgExpand.setRotation(imgExpand.getRotation() + 180);
            if (layout_expand.getVisibility() == View.GONE) {
                layout_expand.setVisibility(View.VISIBLE);
            } else {
                layout_expand.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.btn_edit).setOnClickListener(v -> btnEditAction.accept(people));
        findViewById(R.id.btn_delete).setOnClickListener(v -> {
            runAsync(() -> {
                dataService.delete(people);
                postDeleteAction.run();
            });
        });

        TextView textView = findViewById(R.id.tv_name);
        textView.setText(people.getPseudonym());

        ImageView iconImage = findViewById(R.id.img_people_icon);
        iconImage.setOnClickListener(v -> {
            new ChooseImageDialog(id -> {
                iconImage.setImageResource(id);
                people.setIcon(id.toString());
                runAsync(() -> dataService.update(people));
            }).show();
        });
        String iconName = people.getIcon();
        if (!isBlank(iconName)) {
            DefaultUserIcon.parse(iconName)
                .ifPresent(ico -> iconImage.setImageResource(ico.getResId()));
        } else {
            iconImage.setImageResource(R.drawable.ic_png_contact_default);
        }
    }

    public void setBtnEditAction(Consumer<People> btnEditAction) {
        this.btnEditAction = btnEditAction;
    }

    public void setPostDeleteAction(Runnable postDeleteAction) {
        this.postDeleteAction = postDeleteAction;
    }
}
