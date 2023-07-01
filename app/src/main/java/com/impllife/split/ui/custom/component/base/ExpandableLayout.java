package com.impllife.split.ui.custom.component.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.impllife.split.R;

import static com.impllife.split.service.Util.savePerform;

public class ExpandableLayout extends ConstraintLayout {
    private ImageView imgExpand;
    private TextView  tvTitle;
    private ConstraintLayout clHeader;
    private ConstraintLayout clContent;

    public ExpandableLayout(Context context) {
        super(context);
        init();
    }
    public ExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    public ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    protected void init() {
        init(null);
    }

    protected void init(AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_expandable_layout, this);

        imgExpand = findViewById(R.id.img_expand);
        tvTitle   = findViewById(R.id.tv_title);
        clHeader  = findViewById(R.id.cl_header);
        clContent = findViewById(R.id.cl_content);

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableLayout);
            for (int i = 0; i < array.getIndexCount(); i++) {
                int index = array.getIndex(i);
                if (index == R.styleable.ExpandableLayout_il_title) {
                    savePerform(() -> tvTitle.setText(array.getString(index)));
                }
            }
            array.recycle();
        }
    }
}
