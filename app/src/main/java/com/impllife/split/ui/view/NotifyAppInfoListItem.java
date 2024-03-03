package com.impllife.split.ui.view;

import android.widget.ImageView;
import android.widget.Switch;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotifyAppInfo;
import com.impllife.split.data.jpa.provide.NotifyAppInfoDao;
import com.impllife.split.service.DataService;
import com.impllife.split.ui.custom.adapter.UniversalRVListAdapter;
import com.impllife.split.ui.custom.component.BaseView;

import static com.impllife.split.service.util.Async.runAsync;

public class NotifyAppInfoListItem extends UniversalRVListAdapter.ModelViewData<NotifyAppInfo> {
    private final DataService dataService = DataService.getInstance();
    private final NotifyAppInfoDao notifyAppInfoDao = DataService.getInstance().getDb().getNotifyAppInfoDao();

    public NotifyAppInfoListItem(NotifyAppInfo appInfo) {
        super(R.layout.view_notify_app_info_list_item, appInfo);
    }

    @Override
    public void bindData(BaseView view) {
        NotifyAppInfo appInfo = getData();
        ImageView icon = view.findViewById(R.id.img_app_icon);
        dataService.loadAppIcon(appInfo.getPack()).ifPresent(icon::setImageDrawable);

        view.setTextViewById(R.id.tv_app_name, appInfo.getName());
        Switch btnListen = view.findViewById(R.id.btn_listen);
        btnListen.setChecked(!appInfo.isIgnore());
        btnListen.setOnClickListener(v -> {
            appInfo.setIgnore(!btnListen.isChecked());
            runAsync(() -> notifyAppInfoDao.update(appInfo));
        });
    }
}
