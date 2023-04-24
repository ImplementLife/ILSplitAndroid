package com.impllife.split.ui.fragment;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.service.DataService;
import com.impllife.split.service.NotifyListener;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.view.BaseView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationFragment extends NavFragment {
    private DataService dataService = DataService.getInstance();
    private LinearLayout list;
    private SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        setNavTitle("Notifications");
        init(inflater, view);

        return view;
    }
    private void init(LayoutInflater inflater, View view) {
        view.findViewById(R.id.btn_notify_listener_turn_on).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            MainActivity.getInstance().startActivity(intent);
        });
        view.findViewById(R.id.btn_notify).setOnClickListener(v -> {
            NotifyListener.sendNotification(new SimpleDateFormat().format(new Date()) + " example", MainActivity.getInstance());
        });

        refreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(() -> updateListContent(inflater, view));

        list = view.findViewById(R.id.list);

        updateListContent(inflater, view);
    }

    private void updateListContent(LayoutInflater inflater, View view) {
        runAsync(() -> {
            List<NotificationInfo> allNotifyInfo = dataService.getAllNotifyInfo();
            view.post(() -> {
                list.removeAllViews();
                for (NotificationInfo info : allNotifyInfo) {
                    BaseView baseView = new BaseView(inflater, R.layout.view_notify_list_item, list) {};
                    ImageView icon = baseView.findViewById(R.id.img_app_icon);
                    try {
                        PackageManager pm = MainActivity.getInstance().getPackageManager();
                        ApplicationInfo ai = pm.getApplicationInfo(info.getAppPackage(), 0);
                        Drawable drawable = ai.loadIcon(pm);
                        icon.setImageDrawable(drawable);
                    } catch (PackageManager.NameNotFoundException ignored) {
                        ignored.printStackTrace();
                    }
                    ((TextView) baseView.findViewById(R.id.tv_app_name)).setText(info.getAppName() + " : " + info.getTitle());
                    ((TextView) baseView.findViewById(R.id.tv_dscr)).setText(info.getText());
                    ((TextView) baseView.findViewById(R.id.tv_pack)).setText(info.getAppPackage());
                    list.addView(baseView.getRoot());
                }
                refreshLayout.setRefreshing(false);
            });
        });
    }
}