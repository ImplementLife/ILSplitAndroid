package com.impllife.split.ui.custom.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.impllife.split.R;

public class StatusBar extends ProgressBar {
    public StatusBar(Context context) {
        super(context);
    }
    public StatusBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public StatusBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public StatusBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public synchronized void setProgress(int progress) {
        if (progress >= 0 && progress <= 100) {
            setProgressDrawable(getResources().getDrawable(R.drawable.dr_progress_bar_normal));
        } else if (progress > 100) {
            setProgressDrawable(getResources().getDrawable(R.drawable.dr_progress_bar_over));
            progress -= 100;
        }
        super.setProgress(progress);
    }
}
