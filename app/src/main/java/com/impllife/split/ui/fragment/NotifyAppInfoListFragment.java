package com.impllife.split.ui.fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotifyAppInfo;
import com.impllife.split.data.jpa.provide.NotifyAppInfoDao;
import com.impllife.split.service.DataService;
import com.impllife.split.service.NotifyService;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.custom.component.BaseView;

import java.util.List;
import java.util.stream.Collectors;

public class NotifyAppInfoListFragment extends NavFragment {
    private final DataService dataService = DataService.getInstance();

    private LinearLayout list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_notify_app_info_list, inflater, container);
        setNavTitle("Notifiers");
        init();

        return view;
    }

    protected void init() {
        list = findViewById(R.id.list_items);

        updateListContent();
    }
    private void updateListContent() {
        runAsync(() -> {
            final PackageManager pm = MainActivity.getInstance().getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            NotifyAppInfoDao notifyAppInfoDao = dataService.getDb().getNotifyAppInfoDao();
            List<NotifyAppInfo> l = packages.stream().map((ai) -> {
                NotifyAppInfo appInfo = new NotifyAppInfo();
                appInfo.setName(NotifyService.getAppName(MainActivity.getInstance(), ai));
                appInfo.setPack(ai.packageName);
                NotifyAppInfo byPackage = notifyAppInfoDao.findByPackage(ai.packageName);
                if (byPackage != null) {
                    appInfo.setIgnore(byPackage.isIgnore());
                }
                return appInfo;
            }).collect(Collectors.toList());
            updateView(() -> {
                list.removeAllViews();
                for (NotifyAppInfo info : l) {
                    BaseView view = new BaseView(inflater, R.layout.view_notify_app_info_list_item, list);
                    ImageView icon = view.findViewById(R.id.img_app_icon);
                    dataService.loadAppIcon(info.getPack()).ifPresent(icon::setImageDrawable);

                    view.setTextViewById(R.id.tv_app_name, info.getName());
                    Switch btnListen = view.findViewById(R.id.btn_listen);
                    btnListen.setChecked(!info.isIgnore());
                    btnListen.setOnClickListener(v -> {
                        info.setIgnore(!btnListen.isChecked());
                        runAsync(() -> notifyAppInfoDao.update(info));
                    });
                    list.addView(view.getRoot());
                }
            });
        });
    }
}
