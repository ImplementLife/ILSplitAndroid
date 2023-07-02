package com.impllife.split.ui.custom.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.impllife.split.R;

import static com.impllife.split.service.Util.isBlank;

public class ExpandableLayout extends ConstraintLayout {
    private ImageView imgExpand;
    private TextView  tvTitle;
    private ConstraintLayout clHeader;
    private ConstraintLayout clContent;
    private boolean isInflated;

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
        isInflated = true;

        imgExpand = findViewById(R.id.img_expand);
        tvTitle   = findViewById(R.id.tv_title);
        clHeader  = findViewById(R.id.cl_header);
        clContent = findViewById(R.id.cl_content);

        addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                ViewGroup.LayoutParams rootParam    = v.getLayoutParams();
                ViewGroup.LayoutParams headerParam  = clHeader.getLayoutParams();
                ViewGroup.LayoutParams contentParam = clContent.getLayoutParams();

                if (rootParam.height == LayoutParams.WRAP_CONTENT) {
                    contentParam.height = LayoutParams.WRAP_CONTENT;
                    return;
                }

                int height = rootParam.height - headerParam.height;
                rootParam.height = LayoutParams.WRAP_CONTENT;
                contentParam.height = height;
            }

            @Override
            public void onViewDetachedFromWindow(View v) {}
        });

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableLayout);
            String title = array.getString(R.styleable.ExpandableLayout_il_title);
            if (!isBlank(title)) {
                tvTitle.setText(title);
            }

            array.recycle();
        }

        imgExpand.setRotation(180);
        clHeader.setOnClickListener(v -> {
            if (clContent.getVisibility() != GONE) {
                clContent.setVisibility(GONE);
                imgExpand.setRotation(0);
            } else {
                clContent.setVisibility(VISIBLE);
                imgExpand.setRotation(imgExpand.getRotation() + 180);
            }
        });
    }

    @Override
    public void addView(View child) {
        if (isInflated) {
            clContent.addView(child);
        } else {
            super.addView(child);
        }
    }

    @Override
    public void addView(View child, int index) {
        if (isInflated) {
            clContent.addView(child, index);
        } else {
            super.addView(child, index);
        }
    }

    @Override
    public void addView(View child, int width, int height) {
        if (isInflated) {
            clContent.addView(child, width, height);
        } else {
            super.addView(child, width, height);
        }
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isInflated) {
            clContent.addView(child, params);
        } else {
            super.addView(child, params);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isInflated) {
            clContent.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }
}
