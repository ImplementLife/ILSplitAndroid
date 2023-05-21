package com.impllife.split.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.data.jpa.entity.NotifyAppInfo;
import com.impllife.split.data.jpa.provide.NotifyAppInfoDao;
import com.impllife.split.data.jpa.provide.NotifyInfoDao;
import com.impllife.split.service.DataService;
import com.impllife.split.service.NotifyListener;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.custom.component.BaseView;
import com.impllife.split.ui.custom.component.NavFragment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static androidx.core.app.NotificationManagerCompat.getEnabledListenerPackages;

public class NotificationFragment extends NavFragment {
    private DataService dataService = DataService.getInstance();
    private MainActivity mainActivity = MainActivity.getInstance();
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH.mm.ss");

    private LinearLayout list;
    private SwipeRefreshLayout refreshLayout;
    private Switch btnWork;
    private boolean notifyListenPermit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = createView(R.layout.fragment_notification, inflater, container);
        setNavTitle("Notifications");
        init();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("NotificationFragment", "onResume");
        notifyListenPermit = getEnabledListenerPackages(mainActivity).contains(mainActivity.getPackageName());

        if (notifyListenPermit && /*btnWork.isChecked() != */NotifyListener.isWork()) {
            mainActivity.startForegroundService(new Intent(mainActivity, NotifyListener.class));
        } else if (!notifyListenPermit) {
            NotifyListener.stop();
        }
        btnWork.setChecked(notifyListenPermit && NotifyListener.isWork());
    }

    protected void init() {
        notifyListenPermit = getEnabledListenerPackages(mainActivity).contains(mainActivity.getPackageName());

        btnWork = findViewById(R.id.btn_work);
        btnWork.setOnClickListener(v -> {
            if (btnWork.isChecked()) {
                if (!notifyListenPermit) {
                    mainActivity.startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
                } else {
                    mainActivity.startForegroundService(new Intent(mainActivity, NotifyListener.class));
                }
            } else {
                Log.i("NotificationFragment", "off listener service");
                NotifyListener.stop();
                mainActivity.stopService(new Intent(mainActivity, NotifyListener.class));
            }
        });
        btnWork.setChecked(notifyListenPermit && NotifyListener.isWork());

        findViewById(R.id.btn_notify).setOnClickListener(v -> navController.navigate(R.id.fragment_notify_app_info_list));

        refreshLayout = findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(this::updateListContent);

        list = findViewById(R.id.list);

        updateListContent();
    }

    public static class IgnoreDialog extends DialogFragment {
        private final NotifyAppInfoDao notifyAppInfoDao = DataService.getInstance().getDb().getNotifyAppInfoDao();
        private final NotifyInfoDao notifyInfoDao = DataService.getInstance().getDb().getNotifyInfoDao();
        private final NotificationFragment fragment;
        private final NotificationInfo info;

        public IgnoreDialog(NotificationFragment fragment, NotificationInfo info) {
            this.fragment = fragment;
            this.info = info;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Ignore notifications from this app?")
                .setPositiveButton("Yes", (dialog, id) -> {
                    CompletableFuture.runAsync(() -> {
                        NotifyAppInfo appInfo = notifyAppInfoDao.findByPackage(info.getAppPackage());
                        boolean alreadyExist = appInfo != null;
                        if (!alreadyExist) {
                            appInfo = new NotifyAppInfo(info);
                        }
                        appInfo.setIgnore(true);
                        if (alreadyExist) {
                            notifyAppInfoDao.update(appInfo);
                        } else {
                            notifyAppInfoDao.insert(appInfo);
                        }

                        List<NotificationInfo> allByAppPackage = notifyInfoDao.findAllByAppPackage(info.getAppPackage());
                        if (allByAppPackage.size() > 1) {
                            new DropAnalogDialog(fragment, info).show(getParentFragmentManager(), "drop_analog_dialog");
                        }
                        CompletableFuture.runAsync(() -> {
                            notifyInfoDao.delete(info);
                            fragment.updateListContent();
                        });
                    });
                })
                .setNegativeButton("Cancel", (dialog, id) -> {});
            return builder.create();
        }
    }
    public static class DropAnalogDialog extends DialogFragment {
        private final NotifyInfoDao notifyInfoDao = DataService.getInstance().getDb().getNotifyInfoDao();
        private NotificationInfo info;
        private NotificationFragment fragment;

        public DropAnalogDialog(NotificationFragment fragment, NotificationInfo info) {
            this.info = info;
            this.fragment = fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Drop other info about notifications of this app?")
                .setPositiveButton("Yes", (dialog, id) -> {
                    CompletableFuture.runAsync(() -> {
                        notifyInfoDao.deleteByAppPackage(info.getAppPackage());
                        fragment.updateListContent();
                    });
                })
                .setNegativeButton("No", (dialog, id) -> {});
            return builder.create();
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateListContent() {
        runAsync(() -> {
            List<NotificationInfo> allNotifyInfo = dataService.getAllNotifyInfo();
            updateView(() -> {
                list.removeAllViews();
                for (NotificationInfo info : allNotifyInfo) {
                    BaseView view = new BaseView(inflater, R.layout.view_notify_info_list_item, list);
                    view.getRoot().setLongClickable(true);
                    view.getRoot().setOnLongClickListener(v -> {
                        new IgnoreDialog(this, info).show(this.getParentFragmentManager(), "ignore_dialog");
                        return true;
                    });
                    ImageView icon = view.findViewById(R.id.img_app_icon);
                    dataService.loadAppIcon(info.getAppPackage()).ifPresent(icon::setImageDrawable);
                    view.setTextViewById(R.id.tv_app_name, info.getAppName() + " at " + dateFormat.format(info.getPostDate()));
                    view.setTextViewById(R.id.tv_dscr, info.getTitle() + ": " + info.getText());
                    list.addView(view.getRoot());
                }
                refreshLayout.setRefreshing(false);
            });
        });
    }
}