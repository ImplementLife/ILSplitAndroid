package com.impllife.split.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Switch;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.snackbar.Snackbar;
import com.impllife.split.R;
import com.impllife.split.data.jpa.entity.NotificationInfo;
import com.impllife.split.data.jpa.entity.NotifyAppInfo;
import com.impllife.split.data.jpa.provide.NotifyAppInfoDao;
import com.impllife.split.data.jpa.provide.NotifyInfoDao;
import com.impllife.split.service.DataService;
import com.impllife.split.service.NotifyListener;
import com.impllife.split.service.util.date.DateUtil;
import com.impllife.split.ui.MainActivity;
import com.impllife.split.ui.custom.SwipeToDeleteCallback;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;
import com.impllife.split.ui.custom.component.NavFragment;
import com.impllife.split.ui.view.NotifyInfoListItem;
import com.impllife.split.ui.view.NotifyInfoListItemDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static androidx.core.app.NotificationManagerCompat.getEnabledListenerPackages;
import static com.impllife.split.data.constant.Constant.*;
import static com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter.Data;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class NotifyListFragment extends NavFragment {
    private DataService dataService = DataService.getInstance();
    private NotifyInfoDao notifyInfoDao = DataService.getInstance().getDb().getNotifyInfoDao();
    private MainActivity mainActivity = MainActivity.getInstance();

    private SwipeRefreshLayout refreshLayout;
    private Switch btnWork;
    private boolean notifyListenPermit;

    private AltRecyclerViewListAdapter adapter;
    private RecyclerView recyclerView;
    private NotifyListFragment notifyListFragment;

    public NotifyListFragment() {
        super(R.layout.fragment_notify_list, "Notifications");
        notifyListFragment = this;
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

    @Override
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
        btnWork.setOnLongClickListener(v -> {
            mainActivity.startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
            return false;
        });

        findViewById(R.id.btn_notify).setOnClickListener(v -> navController.navigate(R.id.fragment_notify_app_info_list));

        refreshLayout = findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(this::updateListContent);

        recyclerView = findViewById(R.id.list);
        adapter = new AltRecyclerViewListAdapter();
        recyclerView.setAdapter(adapter);
        updateListContent();
        enableSwipeToDeleteAndUndo();
        getParentFragmentManager().setFragmentResultListener(FRAGMENT_RESULT_KEY, this, (key, bundle) -> {
            if (ACTION_TRN_CREATED_FRAGMENT.equals(bundle.getString(ACTION))) {
                int id = bundle.getInt(NOTIFY_ID, -1);
                boolean isDeleteAfterProcess = SettingsFragment.getProperty(DELETE_NOTIFY_AFTER_PROCESS);
                if (id != -1) {
                    if (isDeleteAfterProcess) {
                        postDelayed(() -> {
                            runAsync(() -> {
                                notifyInfoDao.deleteById(id);
                                post(() -> {
                                    adapter.getData().stream()
                                        .filter(e -> e instanceof NotifyInfoListItem
                                            && ((NotifyInfoListItem) e).getData().getId() == id)
                                        .findFirst().ifPresent(e -> {
                                            int position = adapter.getData().indexOf(e);
                                            adapter.remove(position);
                                        });
                                });
                            });
                        }, 700);
                    } else {
                        runAsync(() -> {
                            NotificationInfo notify = notifyInfoDao.findById(id);
                            notify.setProcessed(true);
                            notifyInfoDao.update(notify);
                            updateListContent();
                        });
                    }
                }
            }
        });
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                Object data = adapter.get(position).getData();
                if (data instanceof NotificationInfo) {
                    runAsync(() -> {
                        NotificationInfo notificationInfo = (NotificationInfo) data;
                        notifyInfoDao.delete(notificationInfo);
                        updateView(() -> {
                            adapter.remove(position);

                            Snackbar snackbar = Snackbar.make(root, "Item removed.", Snackbar.LENGTH_LONG);
                            snackbar.setAction("UNDO", view -> {
                                runAsync(() -> {
                                    notifyInfoDao.insert(notificationInfo);
                                    updateView(() -> {
                                        adapter.add(new NotifyInfoListItem(notifyListFragment, notificationInfo), position);
                                        recyclerView.scrollToPosition(position);
                                    });
                                });
                            });
                            snackbar.setActionTextColor(Color.BLUE);
                            snackbar.show();
                        });
                    });
                }
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    private void updateListContent() {
        runAsync(() -> {
            List<Data> collect = new ArrayList<>();

            Map<Date, List<NotificationInfo>> notifysByDays = dataService.getAllNotifyInfo().stream()
                .collect(groupingBy(e -> DateUtil.getDay(e.getPostDate())));
            List<Date> sortedDays = notifysByDays.keySet().stream().sorted(reverseOrder()).collect(toList());
            for (Date date : sortedDays) {
                collect.add(new NotifyInfoListItemDate(date));
                for (NotificationInfo info : notifysByDays.get(date)) {
                    collect.add(new NotifyInfoListItem(this, info));
                }
            }
            updateView(() -> {
                adapter.replaceAll(collect);
                recyclerView.scrollToPosition(0);
                refreshLayout.setRefreshing(false);
            });
        });
    }

    public static class IgnoreDialog extends DialogFragment {
        private final NotifyAppInfoDao notifyAppInfoDao = DataService.getInstance().getDb().getNotifyAppInfoDao();
        private final NotifyInfoDao notifyInfoDao = DataService.getInstance().getDb().getNotifyInfoDao();
        private final NotifyListFragment fragment;
        private final NotificationInfo info;

        public IgnoreDialog(NotifyListFragment fragment, NotificationInfo info) {
            this.fragment = fragment;
            this.info = info;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
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
        private NotifyListFragment fragment;

        public DropAnalogDialog(NotifyListFragment fragment, NotificationInfo info) {
            this.info = info;
            this.fragment = fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
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
}