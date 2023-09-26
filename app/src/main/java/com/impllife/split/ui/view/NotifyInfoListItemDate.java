package com.impllife.split.ui.view;

import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.service.util.Formatters;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;
import com.impllife.split.ui.custom.component.BaseView;

import java.util.Date;
import java.util.List;

import static com.impllife.split.service.util.Util.isToday;
import static com.impllife.split.service.util.Util.isYesterday;

public class NotifyInfoListItemDate extends AltRecyclerViewListAdapter.Data<Date> {
    private Date date;
    private List<NotificationInfo> notifys;

    public NotifyInfoListItemDate(Date date) {
        super(R.layout.view_notify_list_item_date);
        this.date = date;
    }

    @Override
    public void bindData(BaseView view) {
        view.findViewById(R.id.btn_add_all).setOnClickListener(v -> {

        });
        if (isToday(date)) {
            view.setTextViewById(R.id.tv_date, Formatters.formatDDMM(date) + " Today");
        } else if (isYesterday(date)) {
            view.setTextViewById(R.id.tv_date, Formatters.formatDDMM(date) + " Yesterday");
        } else {
            view.setTextViewById(R.id.tv_date, Formatters.formatDDMM(date));
        }
    }

    @Override
    public Date getData() {
        return date;
    }
}
