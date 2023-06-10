package com.impllife.split.ui.custom.component;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.ui.MainActivity;

public class CustomRecyclerView extends RecyclerView {
    public CustomRecyclerView() {
        super(MainActivity.getInstance());
    }
    public CustomRecyclerView(Context context) {
        super(context);
    }
    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (getLayoutManager() == null) {
            setLayoutManager(new LinearLayoutManager(getContext()));
        }
        super.setAdapter(adapter);
    }
}
