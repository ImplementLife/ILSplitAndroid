package com.impllife.split.ui.view;

import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.service.util.Formatters;
import com.impllife.split.ui.custom.adapter.UniversalRVListAdapter;
import com.impllife.split.ui.custom.component.BaseView;

import java.util.Date;
import java.util.List;

import static com.impllife.split.service.util.date.DateUtil.isToday;
import static com.impllife.split.service.util.date.DateUtil.isYesterday;

public class NotifyInfoListItemDate extends UniversalRVListAdapter.ModelViewData<Date> {
    private List<NotificationInfo> notifys;

    public NotifyInfoListItemDate(Date date) {
        super(R.layout.view_notify_list_item_date, date);
    }

    @Override
    public void bindData(BaseView view) {
        view.findViewById(R.id.btn_add_all).setOnClickListener(v -> {

        });
        if (isToday(data)) {
            view.setTextViewById(R.id.tv_date, Formatters.formatDDMM(data) + " Today");
        } else if (isYesterday(data)) {
            view.setTextViewById(R.id.tv_date, Formatters.formatDDMM(data) + " Yesterday");
        } else {
            view.setTextViewById(R.id.tv_date, Formatters.formatDDMM(data));
        }
    }

}
