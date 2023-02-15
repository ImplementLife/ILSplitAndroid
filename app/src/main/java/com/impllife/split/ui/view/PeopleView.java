package com.impllife.split.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.service.ComService;

import java.util.function.Consumer;

import static java.util.concurrent.CompletableFuture.runAsync;

public class PeopleView extends BaseView {
    private Consumer<People> btnEditAction;
    private Runnable postDeleteAction;
    private People people;

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
        imgExpand.setRotation(-90);
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
                ComService.getInstance().delete(people);
                postDeleteAction.run();
            });
        });

        TextView textView = findViewById(R.id.tv_name);
        textView.setText(people.getPseudonym());
    }

    public void setBtnEditAction(Consumer<People> btnEditAction) {
        this.btnEditAction = btnEditAction;
    }

    public void setPostDeleteAction(Runnable postDeleteAction) {
        this.postDeleteAction = postDeleteAction;
    }
}
