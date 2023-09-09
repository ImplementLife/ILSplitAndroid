package com.impllife.split.ui.view;

import com.impllife.split.R;
import com.impllife.split.service.Formatters;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;
import com.impllife.split.ui.custom.component.BaseView;

import java.util.Date;

import static com.impllife.split.service.Util.isToday;
import static com.impllife.split.service.Util.isYesterday;

public class NotifyInfoListItemDate extends AltRecyclerViewListAdapter.Data<Date> {
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
            view.setTextViewById(R.id.tv_date, Formatters.formatDDMM(date));
        }
    }

    @Override
    public Date getData() {
        return date;
    }
}
