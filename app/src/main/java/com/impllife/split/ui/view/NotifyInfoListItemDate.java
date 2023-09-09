package com.impllife.split.ui.view;

import android.annotation.SuppressLint;
import com.impllife.split.R;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;
import com.impllife.split.ui.custom.component.BaseView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.impllife.split.service.Util.isToday;
import static com.impllife.split.service.Util.isYesterday;

public class NotifyInfoListItemDate extends AltRecyclerViewListAdapter.Data<Date> {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");

    private Date date;

    public NotifyInfoListItemDate(Date date) {
        super(R.layout.view_notify_list_item_date);
        this.date = date;
    }

    @Override
    public void bindData(BaseView view) {
        if (isToday(date)) {
            view.setTextViewById(R.id.tv_date, "Today");
        } else if (isYesterday(date)) {
            view.setTextViewById(R.id.tv_date, "Yesterday");
        } else {
            view.setTextViewById(R.id.tv_date, dateFormat.format(date));
        }
    }

    @Override
    public Date getData() {
        return date;
    }
}
